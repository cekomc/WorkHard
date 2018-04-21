package org.softuni.work.areas.businesPartners.services.classes;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.softuni.work.areas.businesPartners.repositories.BusinessPartnerRepository;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.softuni.work.areas.roles.entities.BusinessRole;
import org.softuni.work.areas.roles.services.interfaces.BusinessRoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BusinessPartnerServiceImpl implements BusinessPartnerService{
    private final ModelMapper mapper;
    private final BusinessPartnerRepository businessPartnerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BusinessRoleService roleService;


    public BusinessPartnerServiceImpl(ModelMapper mapper, BusinessPartnerRepository businessPartnerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, BusinessRoleService roleService1) {
        this.mapper = mapper;
        this.businessPartnerRepository = businessPartnerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService1;
    }


    @Override
    public void register(BusinessPartnerRegisterBindingModel bindingModel) {
        BusinessPartner businessPartner = this.mapper.map(bindingModel, BusinessPartner.class);

        businessPartner.setPassword(this.bCryptPasswordEncoder.encode(businessPartner.getPassword()));

        BusinessRole role = this.roleService.getRole("ROLE_BUSINESS_PARTNER");
        businessPartner.getRoles().add(role);

        this.roleService.create(role);
        this.businessPartnerRepository.save(businessPartner);

    }

    @Override
    public void login(BusinessPartnerLoginBindingModel bindingModel) throws Exception {
        BusinessPartner check = this.businessPartnerRepository.findFirstByEmail(bindingModel.getEmail());
        String bug = "";
        if (check == null) {
            throw new Exception("Email not found");
        } else if (!bCryptPasswordEncoder.matches(bindingModel.getPassword(), check.getPassword())){
            throw new Exception("Password not found");
        }
    }

    @Override
    public BusinessPartner findByEmail(String email) {
        return this.businessPartnerRepository.findFirstByEmail(email);
    }

}
