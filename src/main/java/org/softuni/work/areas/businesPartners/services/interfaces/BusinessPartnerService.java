package org.softuni.work.areas.businesPartners.services.interfaces;

import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerLoginBindingModel;
import org.softuni.work.areas.businesPartners.models.BusinessPartnerRegisterBindingModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface BusinessPartnerService {
    void register(BusinessPartnerRegisterBindingModel bindingModel);

    void login(BusinessPartnerLoginBindingModel bindingModel) throws Exception;

    BusinessPartner findByEmail(String email);

    void loginBusinessPartnerAndCheckForErrors(BusinessPartnerLoginBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, Model model, RedirectAttributes redirectAttributes);

    void getBusinessPartnerRegister(Model model);

    void registerBusinessPartner(BusinessPartnerRegisterBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes);
}
