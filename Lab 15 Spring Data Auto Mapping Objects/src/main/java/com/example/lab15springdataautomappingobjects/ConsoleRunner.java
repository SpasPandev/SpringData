package com.example.lab15springdataautomappingobjects;

import com.example.lab15springdataautomappingobjects.dto.Employee3Dto;
import com.example.lab15springdataautomappingobjects.dto.EmployeeDto;
import com.example.lab15springdataautomappingobjects.dto.Manager2Dto;
import com.example.lab15springdataautomappingobjects.services.Employee2Service;
import com.example.lab15springdataautomappingobjects.services.Employee3Service;
import com.example.lab15springdataautomappingobjects.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final Employee2Service employee2Service;
    private final Employee3Service employee3Service;

    public ConsoleRunner(EmployeeService employeeService, Employee2Service employee2Service, Employee3Service employee3Service) {
        this.employeeService = employeeService;
        this.employee2Service = employee2Service;
        this.employee3Service = employee3Service;
    }

    @Override
    public void run(String... args){

        // 1. Simple Mapping
        EmployeeDto employeeDto = employeeService.task1(1L);

        System.out.println(employeeDto.getFirstName() + " " + employeeDto.getLastName() + " " + employeeDto.getSalary());


        // 2. Advanced Mapping
        Manager2Dto manager2Dto = employee2Service.task2(1L);

        System.out.println(manager2Dto.getFirstName() + " " + manager2Dto.getLastName() + " | Employees: " +
                manager2Dto.getInChargeOf().size());
        manager2Dto.getInChargeOf().forEach(emp -> {
            System.out.println("\t - " + emp.getFirstName() + " "  + emp.getLastName() + " "  + emp.getSalary());
        });

        // 3. Projection

        List<Employee3Dto> listOfEmployees3Dtos = employee3Service.task3();

        listOfEmployees3Dtos
                .stream()
                .map(emp3Dto ->
                        String.format("%s %s %.2f - Manager: %s",
                                emp3Dto.getFirstName(), emp3Dto.getLastName(),
                                emp3Dto.getSalary(), emp3Dto.getManagerLastName()))
                .forEach(System.out::println);
    }
}
