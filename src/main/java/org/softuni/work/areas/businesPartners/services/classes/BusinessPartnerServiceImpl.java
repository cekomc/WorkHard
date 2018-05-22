package org.softuni.work.areas.businesPartners.services.classes;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.softuni.work.areas.businesPartners.repositories.BusinessPartnerRepository;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.softuni.work.areas.roles.entities.BusinessRole;
import org.softuni.work.areas.roles.services.interfaces.BusinessRoleService;
import org.softuni.work.areas.workers.entities.Worker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void loginBusinessPartnerAndCheckForErrors(@Valid BusinessPartnerLoginBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.businessPartnerInput", bindingResult);
            redirectAttributes.addFlashAttribute("businessPartnerInput", bindingModel);

            modelAndView.setViewName("redirect:/business-login");
        }else{
            modelAndView.setViewName("redirect:/business-home");
        }
        try {
            login(bindingModel);

        } catch (Exception e) {
            String errorMsg =e.getMessage();
            model.addAttribute("errorMsg", errorMsg);
            modelAndView.setViewName("/business-home");

        }
    }

    @Override
    public void getBusinessPartnerRegister(Model model) {
        if (!model.containsAttribute("businessPartnerInput")) {
            model.addAttribute("businessPartnerInput",
                    new BusinessPartnerRegisterBindingModel());
        }
    }

    @Override
    public void registerBusinessPartner(@Valid BusinessPartnerRegisterBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.businessPartnerInput", bindingResult);
            redirectAttributes.addFlashAttribute("businessPartnerInput", bindingModel);

            modelAndView.setViewName("redirect:/business-register");
        } else {
            modelAndView.setViewName("redirect:/business-login");
            register(bindingModel);
        }
    }



    @Override
    public BusinessPartner findByEmail(String email) {
        return this.businessPartnerRepository.findFirstByEmail(email);
    }


}
