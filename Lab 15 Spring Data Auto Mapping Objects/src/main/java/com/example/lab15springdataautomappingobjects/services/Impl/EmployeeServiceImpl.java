package com.example.lab15springdataautomappingobjects.services.Impl;

import com.example.lab15springdataautomappingobjects.dto.EmployeeDto;
import com.example.lab15springdataautomappingobjects.repositories.EmployeeRepository;
import com.example.lab15springdataautomappingobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto task1(Long id) {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(
                employeeRepository.findById(id).orElseThrow(), EmployeeDto.class
        );
    }
}
