package com.example.workshop_mvc_project.repository;

import com.example.workshop_mvc_project.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsAllBy();

    List<Employee> findAllByAgeAfter(int age);
}
