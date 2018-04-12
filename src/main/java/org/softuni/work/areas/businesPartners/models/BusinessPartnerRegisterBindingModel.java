package org.softuni.work.areas.businesPartners.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BusinessPartnerRegisterBindingModel {

    @NotEmpty(message = "FirstName cannot be null!")
    private String firstName;

    @NotEmpty(message = "LastName cannot be null!")
    private String lastName;

    @NotEmpty(message = "Email cannot be null!")
    @Email(message = "Not a valid email")
    private String email;

    @NotEmpty(message = "Password cannot be null!")
    @Size(min= 8, max = 20, message = "Password should not be lass than 8 symbols!")
    private String password;

    @NotEmpty(message = "CompanyName cannot be null!")
    private String companyName;

    public BusinessPartnerRegisterBindingModel() {
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
}
