package org.softuni.work.areas.projects.service.interfaces;

import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectCreateBindingModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProjectService {
    void create(ProjectCreateBindingModel bindingModel, String email);

    List<Project> findAllProjects();

    Project findProjectById(String id);

    void saveEditedProject(Project project);

    void deleteProject(String id);

    ModelAndView geCreatedApps(@ModelAttribute ProjectCreateBindingModel bindingModel, ModelAndView modelAndView);
}
