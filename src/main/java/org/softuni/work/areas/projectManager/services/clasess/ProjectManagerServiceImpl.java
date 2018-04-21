package org.softuni.work.areas.projectManager.services.clasess;

import org.softuni.work.areas.projectManager.repositories.ProjectManagerRepository;
import org.softuni.work.areas.projectManager.services.interfaces.ProjectManagerService;
import org.softuni.work.areas.workers.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService {
    private final ProjectManagerRepository repository;

    @Autowired
    public ProjectManagerServiceImpl(ProjectManagerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Worker> listAllWorkers() {
        return this.repository.findAll();
    }
}
