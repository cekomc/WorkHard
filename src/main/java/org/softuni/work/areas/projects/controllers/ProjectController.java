package org.softuni.work.areas.projects.controllers;

import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.softuni.work.areas.projects.models.ProjectCreateBindingModel;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class ProjectController {
    private final ProjectService projectService;
    private final BusinessPartnerService businessPartnerService;

    @Autowired
    public ProjectController(ProjectService projectService, BusinessPartnerService businessPartnerService) {
        this.projectService = projectService;
        this.businessPartnerService = businessPartnerService;
    }


    @GetMapping("/business-create-app")
    public ModelAndView businessCreteApp(){
        return new ModelAndView("business-create-app");
    }

    @PostMapping("/business-create-app")
    public ModelAndView registerConfirm(@ModelAttribute ProjectCreateBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/business-home");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email ="murata@abv.bg";
        BusinessPartner businessPartner = this.businessPartnerService.findByEmail(email);
        String getEmail = businessPartner.getEmail();
        this.projectService.create(bindingModel, getEmail);
        return modelAndView;
    }
}
