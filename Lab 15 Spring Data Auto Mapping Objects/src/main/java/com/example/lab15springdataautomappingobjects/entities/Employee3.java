package com.example.lab15springdataautomappingobjects.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees3")
public class Employee3 {


    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private Date birthday;
    private String address;
    private Employee3 manager;

    public Employee3() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne
    public Employee3 getManager() {
        return manager;
    }

    public void setManager(Employee3 manager) {
        this.manager = manager;
    }
}
