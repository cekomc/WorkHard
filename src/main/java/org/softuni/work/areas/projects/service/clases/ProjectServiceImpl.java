package org.softuni.work.areas.projects.service.clases;

import org.modelmapper.ModelMapper;
import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.businesPartners.repositories.BusinessPartnerRepository;
import org.softuni.work.areas.businesPartners.services.interfaces.BusinessPartnerService;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectCreateBindingModel;
import org.softuni.work.areas.projects.repositories.ProjectRepository;
import org.softuni.work.areas.projects.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final BusinessPartnerService businessPartnerService;
    private final ModelMapper mapper;
    private final BusinessPartnerRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, BusinessPartnerService businessPartnerService, ModelMapper mapper, BusinessPartnerRepository repository) {
        this.projectRepository = projectRepository;
        this.businessPartnerService = businessPartnerService;
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public void create(ProjectCreateBindingModel bindingModel, String email) {
        Project project = this.mapper.map(bindingModel, Project.class);
        BusinessPartner businessPartner = this.repository.findFirstByEmail(email);
        businessPartner.getProjectList().add(project);
        project.setBusinessPartner(businessPartner);
        this.projectRepository.save(project);
    }

    @Override
    public List<Project> findAllProjects() {
        return this.projectRepository.findAll();

    }

    @Override
    public Project findProjectById(String id) {
        return this.projectRepository.findFirstById(id);
    }

    @Override
    public void saveEditedProject(Project project) {
        this.projectRepository.save(project);
    }

    @Override
    public void deleteProject(String id) {
        Project project = this.projectRepository.findFirstById(id);
        this.projectRepository.delete(project);
    }

    @Override
    public ModelAndView geCreatedApps(@ModelAttribute ProjectCreateBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/business-home");
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = "ruka@abv.bg";
        BusinessPartner businessPartner = this.businessPartnerService.findByEmail(email);
        String getEmail = businessPartner.getEmail();
        this.create(bindingModel, getEmail);
        return modelAndView;
    }

}
