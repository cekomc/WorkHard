package org.softuni.work.areas.workers.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.roles.entities.Role;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Worker {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull(message = "Name cannot be null")
    private String fullName;

    @NotNull
    @Min(value = 8, message = "Password should not be lass than 8 symbols")
    private String password;

    @Email( message = "Not a valid email")
    private String email;

    @NotNull(message = "Cv cannot be empty")
    private String cv;

    @ManyToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "worker_project",
            joinColumns = {
                    @JoinColumn(
                            name = "worker_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "project_id",
                            referencedColumnName = "id"
                    )
            }
    )
    private List<Project> projectList;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_roles",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Worker() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCv() {
        return cv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
