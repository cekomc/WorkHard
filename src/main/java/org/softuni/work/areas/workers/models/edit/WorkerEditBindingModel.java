package org.softuni.work.areas.workers.models.edit;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class WorkerEditBindingModel {
    @NotEmpty(message = "Name cannot be null!")
    private String fullName;

    @NotEmpty(message = "Email cannot be null!")
    @Email(message = "Email is not correct")
    private String email;

    @NotEmpty(message = "Password cannot be null!")
    @Size(min= 8, max = 20, message = "Password should not be lass than 8 symbols!")
    private String password;

    public WorkerEditBindingModel() {
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

}
