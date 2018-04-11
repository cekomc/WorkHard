package org.softuni.work.areas.businesPartners.controllers;

import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BusinessAccountController {
    private final BusinessPartnerService businessPartnerService;

    @Autowired
    public BusinessAccountController(BusinessPartnerService businessPartnerService) {
        this.businessPartnerService = businessPartnerService;
    }

    @GetMapping("/business-login")
    public ModelAndView businessLogin(){
        return new ModelAndView("business-login");
    }

    @PostMapping("/business-login")
    public ModelAndView loginConfirm(@ModelAttribute BusinessPartnerLoginBindingModel bindingModel, ModelAndView modelAndView) throws Exception {
        modelAndView.setViewName("redirect:/business-home");
        this.businessPartnerService.login(bindingModel);
        return modelAndView;
    }

    @GetMapping("/business-register")
    public ModelAndView businessRegister(){
        return new ModelAndView("business-register");
    }

    @PostMapping("/business-register")
    public ModelAndView registerConfirm(@ModelAttribute BusinessPartnerRegisterBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/business-login");
        this.businessPartnerService.register(bindingModel);
        return modelAndView;
    }
}





