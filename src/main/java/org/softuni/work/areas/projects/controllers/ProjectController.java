package org.softuni.work.areas.projects.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.softuni.work.areas.projects.models.ProjectCreateBindingModel;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/business-create-app")
    public ModelAndView businessCreteApp(){
        return new ModelAndView("business-create-app");
    }

    @PostMapping("/business-create-app")
    public ModelAndView registerConfirm(@ModelAttribute ProjectCreateBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/business-home");
        this.projectService.create(bindingModel);
        return modelAndView;
    }
}
