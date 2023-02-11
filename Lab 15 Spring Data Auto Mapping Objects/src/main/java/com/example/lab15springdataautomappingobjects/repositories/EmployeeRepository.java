package com.example.lab15springdataautomappingobjects.repositories;

import com.example.lab15springdataautomappingobjects.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
