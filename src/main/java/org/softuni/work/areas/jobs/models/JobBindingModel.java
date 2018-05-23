package org.softuni.work.areas.jobs.models;


import org.hibernate.validator.constraints.NotEmpty;
import org.softuni.work.areas.workers.entities.Worker;

import java.util.List;

public class JobBindingModel {
    @NotEmpty(message = "The name of the Job cannot be null!")
    private String name;

    @NotEmpty(message = "Description cannot be null!")
    private String description;

    @NotEmpty(message = "Requirements cannot be null!")
    private String requirements;

    private boolean available;

    private List<Worker>  workerList;
    public JobBindingModel() {
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
