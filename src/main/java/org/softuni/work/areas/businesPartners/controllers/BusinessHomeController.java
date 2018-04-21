package org.softuni.work.areas.businesPartners.controllers;

import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessHomeController {
    private final ProjectService projectService;

    @Autowired
    public BusinessHomeController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/business-my-apps")
    public ModelAndView businessMyApp(ModelAndView modelAndView){
        modelAndView.setViewName("business-my-apps");
        String email = "murata@abv.bg";
        List<Project> allProjects = this.projectService.findAllProjects();
        List<Project> myProjects = findMyProjects(allProjects, email);
        modelAndView.addObject("projects", myProjects);
        return modelAndView;
    }

    private List<Project> findMyProjects(List<Project> allProjects, String email) {
        List<Project> myProjects = new ArrayList<>();
        for (Project project : allProjects) {
            String debug = project.getBusinessPartner().getEmail();
            if(project.getBusinessPartner().getEmail().equals(email)){
                myProjects.add(project);
            }
        }
        return myProjects;
    }


}
