package org.softuni.work.areas.projects.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.softuni.work.areas.workers.entities.Worker;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull(message = "Project name cannot be null!")
    private String name;

    @NotNull(message = "Description cannot be null!")
    private String description;

    private boolean isAssigned;

    @ManyToOne
    @JoinColumn(name="business_partner_id")
    private BusinessPartner businessPartner;

    @ManyToMany(mappedBy = "projectList",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Worker> workerList;

    public Project() {
    }

    public BusinessPartner getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }
}
