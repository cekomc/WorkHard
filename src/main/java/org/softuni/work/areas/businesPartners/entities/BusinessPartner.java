package org.softuni.work.areas.businesPartners.entities;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.softuni.work.areas.projects.entities.Project;
import org.softuni.work.areas.roles.entities.BusinessRole;
import org.softuni.work.areas.roles.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class BusinessPartner {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "businessPartner") // inverse side: it has a mappedBy attribute, and can't decide how the association is mapped, since the other side already decided it.
    private List<Project> projectList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "business_roles",
            joinColumns = @JoinColumn(name = "business_partner_id"),
            inverseJoinColumns = @JoinColumn(name = "business_role_id"))
    private Set<BusinessRole> roles;

    public BusinessPartner() {
        this.roles = new HashSet<>();
    }

    public Set<BusinessRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<BusinessRole> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
