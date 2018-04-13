package org.softuni.work.areas.businesPartners.services.classes;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.softuni.work.areas.businesPartners.repositories.BusinessPartnerRepository;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BusinessPartnerServiceImpl implements BusinessPartnerService {
    private final ModelMapper mapper;
    private final BusinessPartnerRepository businessPartnerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public BusinessPartnerServiceImpl(ModelMapper mapper, BusinessPartnerRepository workerRepository, BusinessPartnerRepository businessPartnerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mapper = mapper;
        this.businessPartnerRepository = businessPartnerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void register(BusinessPartnerRegisterBindingModel bindingModel) {
        BusinessPartner businessPartner = this.mapper.map(bindingModel, BusinessPartner.class);
        businessPartner.setPassword(this.bCryptPasswordEncoder.encode(businessPartner.getPassword()));
        this.businessPartnerRepository.save(businessPartner);

    }

    @Override
    public void login(BusinessPartnerLoginBindingModel bindingModel) throws Exception {
        BusinessPartner check = this.businessPartnerRepository.findFirstByEmail(bindingModel.getEmail());
        if (check == null) {
            throw new Exception("Email not found");
        } else if (!bCryptPasswordEncoder.matches(bindingModel.getPassword(), check.getPassword())){
            throw new Exception("Password not found");
        }
    }


}
