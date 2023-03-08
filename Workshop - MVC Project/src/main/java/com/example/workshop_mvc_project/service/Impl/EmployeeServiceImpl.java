package com.example.workshop_mvc_project.service.Impl;

import com.example.workshop_mvc_project.model.dto.EmployeeRootDto;
import com.example.workshop_mvc_project.model.dto.ExportEmployeeDto;
import com.example.workshop_mvc_project.model.dto.ImportEmployeesInputStringDto;
import com.example.workshop_mvc_project.model.entity.Employee;
import com.example.workshop_mvc_project.repository.EmployeeRepository;
import com.example.workshop_mvc_project.service.EmployeeService;
import com.example.workshop_mvc_project.service.ProjectService;
import com.example.workshop_mvc_project.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_FILE_PATH_NAME = "src/main/resources/files/xmls/employees.xml";
    private final EmployeeRepository employeeRepository;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectService projectService, ModelMapper modelMapper, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean exist() {
        return employeeRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() {

        try {
            return Files.readString(Path.of(EMPLOYEES_FILE_PATH_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importToDb(ImportEmployeesInputStringDto employeesInput) {

        EmployeeRootDto employeeRootDto = xmlParser.deserialize(employeesInput.getEmployees(),
                EmployeeRootDto.class);

        employeeRootDto.getEmployees()
                .stream()
                .map(employeeDto -> {
                    Employee employee = modelMapper.map(employeeDto, Employee.class);
                    employee.setProject(projectService.findProjectByName(employee.getProject().getName()));

                    return employee;
                })
                .forEach(employeeRepository::save);

    }

    @Override
    public Object exportAllEmployeesAbove25() {

        StringBuilder stringBuilder = new StringBuilder();

        employeeRepository.findAllByAgeAfter(25)
                .stream()
                .map(employee -> modelMapper.map(employee, ExportEmployeeDto.class))
                .forEach(exportEmployeeDto -> stringBuilder
                        .append(getInfoToString(exportEmployeeDto))
                        .append(System.lineSeparator()));

        return stringBuilder;
    }

    private String getInfoToString(ExportEmployeeDto exportEmployeeDto) {

        return "Name: " + exportEmployeeDto.getFirstName() + " " + exportEmployeeDto.getLastName() + "\n" +
                "\tAge: " + exportEmployeeDto.getAge() + "\n" +
                "\tProject name: " + exportEmployeeDto.getProjectName();
    }
}
