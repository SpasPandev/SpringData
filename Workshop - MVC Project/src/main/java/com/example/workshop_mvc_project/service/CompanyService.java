package com.example.workshop_mvc_project.service;

import com.example.workshop_mvc_project.model.dto.ImportCompaniesInputStringDto;
import com.example.workshop_mvc_project.model.entity.Company;

public interface CompanyService {

    boolean exist();

    String getXmlForImport();

    void importToDb(ImportCompaniesInputStringDto companiesInput);

    Company findCompanyByName(String name);
}
