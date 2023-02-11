package com.example.lab15springdataautomappingobjects.repositories;

import com.example.lab15springdataautomappingobjects.entities.Employee2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Employee2Repository extends JpaRepository<Employee2, Long> {

}
