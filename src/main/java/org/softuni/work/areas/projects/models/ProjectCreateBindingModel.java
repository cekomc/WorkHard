package org.softuni.work.areas.projects.models;

public class ProjectCreateBindingModel {
    private String name;

    private String description;

    public ProjectCreateBindingModel() {
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
