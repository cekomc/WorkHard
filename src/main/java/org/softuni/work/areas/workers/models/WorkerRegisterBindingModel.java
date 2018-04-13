package org.softuni.work.areas.workers.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class WorkerRegisterBindingModel {
    @NotEmpty(message = "Name cannot be null!")
    private String fullName;

    @NotEmpty(message = "Email cannot be null!")
    @Email(message = "Email is not correct")
    private String email;

    @NotEmpty(message = "Password cannot be null!")
    @Size(min= 8, max = 20, message = "Password should not be lass than 8 symbols!")
    private String password;

    @NotEmpty(message = "Cv cannot be null!")
    private String cv;

    public WorkerRegisterBindingModel() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
