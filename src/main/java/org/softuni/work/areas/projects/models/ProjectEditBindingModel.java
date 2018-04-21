package org.softuni.work.areas.projects.models;

import org.softuni.work.areas.workers.entities.Worker;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.util.List;

public class ProjectEditBindingModel {
    @NotEmpty(message = "Name cannot be empty!")
private String id;

    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotEmpty(message = "Description cannot be empty!")
    private String description;

    @NotEmpty(message = "Email cannot be empty!")
    private List<String> workersId;

    @NotEmpty(message = "Link cannot be empty!")
    private String link;

    public ProjectEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public List<String> getWorkersId() {
        return workersId;
    }

    public void setWorkersId(List<String> workersId) {
        this.workersId = workersId;
    }
}
