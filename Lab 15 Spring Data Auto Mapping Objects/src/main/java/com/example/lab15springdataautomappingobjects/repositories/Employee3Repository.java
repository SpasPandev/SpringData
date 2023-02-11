package com.example.lab15springdataautomappingobjects.repositories;

import com.example.lab15springdataautomappingobjects.entities.Employee3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Employee3Repository extends JpaRepository<Employee3, Long> {

    @Query("select e from Employee3 e where e.birthday < '1990-01-01' order by e.salary desc")
    List<Employee3> findAllBornBefore1990OrderBySalaryDesc();
}
