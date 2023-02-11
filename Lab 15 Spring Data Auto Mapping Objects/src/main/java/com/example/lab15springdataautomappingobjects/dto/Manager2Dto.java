package com.example.lab15springdataautomappingobjects.dto;

import java.util.List;

public class Manager2Dto {

    private String firstName;
    private String lastName;
    private List<Employee2Dto> inChargeOf;

    public Manager2Dto() {
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

    public List<Employee2Dto> getInChargeOf() {
        return inChargeOf;
    }

    public void setInChargeOf(List<Employee2Dto> inChargeOf) {
        this.inChargeOf = inChargeOf;
    }
}
