package org.softuni.work.areas.workers.models;

import org.hibernate.validator.constraints.NotEmpty;

public class WorkerLoginBindingModel{
    @NotEmpty(message = "Email cannot be empty!")
    private String email;

    @NotEmpty(message = "Password cannot be empty!")
    private String password;

    public WorkerLoginBindingModel() {
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
}
