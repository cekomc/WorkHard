package org.softuni.work.areas.workers.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.work.areas.jobs.entities.Job;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.roles.entities.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Worker{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cv;

    private boolean hassApplied;

    private boolean isAproved;

    @ManyToMany(mappedBy="workerList")
    private List<Project> projectList;

    @ManyToOne
    @JoinTable(
            name="workers_jobs",
            joinColumns = @JoinColumn( name="worker_id"),
            inverseJoinColumns = @JoinColumn( name="job_id")
    )
    private Job job;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_roles",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Worker() {
        this.roles = new HashSet<>();
    }

    public boolean isHassApplied() {
        return hassApplied;
    }

    public void setHassApplied(boolean hassApplied) {
        this.hassApplied = hassApplied;
    }

    public boolean isAproved() {
        return isAproved;
    }

    public void setAproved(boolean aproved) {
        isAproved = aproved;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
