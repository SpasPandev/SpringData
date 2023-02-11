package com.example.lab15springdataautomappingobjects.services.Impl;

import com.example.lab15springdataautomappingobjects.dto.Manager2Dto;
import com.example.lab15springdataautomappingobjects.repositories.Employee2Repository;
import com.example.lab15springdataautomappingobjects.services.Employee2Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Employee2ServiceImpl implements Employee2Service {

    private final Employee2Repository employee2Repository;

    public Employee2ServiceImpl(Employee2Repository employee2Repository) {
        this.employee2Repository = employee2Repository;
    }

    @Override
    public Manager2Dto task2(Long id) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(
                employee2Repository.findById(id).orElseThrow(), Manager2Dto.class
        );
    }
}
