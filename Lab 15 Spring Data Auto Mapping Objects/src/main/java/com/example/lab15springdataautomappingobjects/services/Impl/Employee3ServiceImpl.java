package com.example.lab15springdataautomappingobjects.services.Impl;

import com.example.lab15springdataautomappingobjects.dto.Employee3Dto;
import com.example.lab15springdataautomappingobjects.entities.Employee3;
import com.example.lab15springdataautomappingobjects.repositories.Employee3Repository;
import com.example.lab15springdataautomappingobjects.services.Employee3Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Employee3ServiceImpl implements Employee3Service {

    private final Employee3Repository employee3Repository;

    public Employee3ServiceImpl(Employee3Repository employee3Repository) {
        this.employee3Repository = employee3Repository;
    }

    @Override
    public List<Employee3Dto> task3() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(Employee3.class, Employee3Dto.class)
                .addMappings(mapper ->
                        mapper.map(employee3ManagerLastName ->
                                employee3ManagerLastName.getManager().getLastName(),
                                Employee3Dto::setManagerLastName));

        modelMapper
                .map(employee3Repository.findAll(), Employee3Dto.class);

        List<Employee3> allEmployees3 = employee3Repository.findAllBornBefore1990OrderBySalaryDesc();

        return allEmployees3
                .stream()
                .map(e ->
                        modelMapper.map(e, Employee3Dto.class))
                .collect(Collectors.toList());

    }
}
