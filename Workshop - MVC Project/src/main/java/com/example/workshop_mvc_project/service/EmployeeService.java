package com.example.workshop_mvc_project.service;

import com.example.workshop_mvc_project.model.dto.ImportEmployeesInputStringDto;

public interface EmployeeService {

    boolean exist();

    String getXmlForImport();

    void importToDb(ImportEmployeesInputStringDto employeesInput);

    Object exportAllEmployeesAbove25();
}
