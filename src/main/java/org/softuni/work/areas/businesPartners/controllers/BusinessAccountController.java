package org.softuni.work.areas.businesPartners.controllers;

import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BusinessAccountController {
    private final BusinessPartnerService businessPartnerService;

    @Autowired
    public BusinessAccountController(BusinessPartnerService businessPartnerService) {
        this.businessPartnerService = businessPartnerService;
    }

    @GetMapping("/business-login")
    public ModelAndView businessLogin(Model model) {
        if (!model.containsAttribute("businessPartnerInput"))
            model.addAttribute("businessPartnerInput", new BusinessPartnerLoginBindingModel());
        return new ModelAndView("business-login");
    }

  @PostMapping("/business-login")
  public ModelAndView loginConfirm(@Valid BusinessPartnerLoginBindingModel bindingModel,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView,
                                          Model model,
                                          RedirectAttributes redirectAttributes) throws Exception {

       businessPartnerService.loginBusinessPartnerAndCheckForErrors(bindingModel, bindingResult, modelAndView, model, redirectAttributes);
       return modelAndView;
   }


    @GetMapping("/business-register")
    public ModelAndView businessRegister(Model model) {
        businessPartnerService.getBusinessPartnerRegister(model);
        return new ModelAndView("business-register");
    }

    @PostMapping("/business-register")
        public ModelAndView registerConfirm(@Valid BusinessPartnerRegisterBindingModel bindingModel,
                                            BindingResult bindingResult,
                                            ModelAndView modelAndView,
                                            RedirectAttributes redirectAttributes){

        businessPartnerService.registerBusinessPartner(bindingModel, bindingResult, modelAndView, redirectAttributes);
        return modelAndView;
        }


}





