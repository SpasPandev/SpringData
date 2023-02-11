package com.example.lab15springdataautomappingobjects.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees2")
public class Employee2 {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private BigDecimal salary;
    private Boolean isOnHoliday;
    private String address;
    private Employee2 manager;
    private List<Employee2> inChargeOf;

    public Employee2() {
    }

    public Employee2(Long id, String firstName, String lastName, Date birthday, BigDecimal salary, Boolean isOnHoliday, String address, Employee2 manager, List<Employee2> inChargeOf) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.salary = salary;
        this.isOnHoliday = isOnHoliday;
        this.address = address;
        this.manager = manager;
        this.inChargeOf = inChargeOf;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Boolean getOnHoliday() {
        return isOnHoliday;
    }

    public void setOnHoliday(Boolean onHoliday) {
        isOnHoliday = onHoliday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne
    public Employee2 getManager() {
        return manager;
    }

    public void setManager(Employee2 manager) {
        this.manager = manager;
    }

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    public List<Employee2> getInChargeOf() {
        return inChargeOf;
    }

    public void setInChargeOf(List<Employee2> inChargeOf) {
        this.inChargeOf = inChargeOf;
    }
}
