package org.softuni.work.areas.workers.models.edit;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class WorkerAdminBindingModel {
    @NotEmpty(message = "Id")
    private String id;

    @NotEmpty(message = "Name cannot be null!")
    private String fullName;

    @NotEmpty(message = "Email cannot be null!")
    @Email(message = "Email is not correct")
    private String email;

    @NotEmpty(message = "Password cannot be null!")
    private String roleId;

    public WorkerAdminBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
