package org.softuni.work.areas.projects.service.interfaces;

import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.projects.models.ProjectCreateBindingModel;

import java.util.List;

public interface ProjectService {
    void create(ProjectCreateBindingModel bindingModel, String email);

    List<Project> findAllProjects();

    Project findProjectById(String id);

    void saveEditedProject(Project project);

    void deleteProject(String id);
}
