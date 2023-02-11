package com.example.lab15springdataautomappingobjects.dto;

import com.example.lab15springdataautomappingobjects.entities.Employee3;

import java.math.BigDecimal;
import java.util.Date;

public class Employee3Dto {


    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String managerLastName;

    public Employee3Dto() {
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }
}
