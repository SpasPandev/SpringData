package com.example.workshop_mvc_project.service.Impl;

import com.example.workshop_mvc_project.model.dto.CompanyRootDto;
import com.example.workshop_mvc_project.model.dto.ImportCompaniesInputStringDto;
import com.example.workshop_mvc_project.model.entity.Company;
import com.example.workshop_mvc_project.repository.CompanyRepository;
import com.example.workshop_mvc_project.service.CompanyService;
import com.example.workshop_mvc_project.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String COMPANIES_FILE_PATH_NAME = "src/main/resources/files/xmls/companies.xml";
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean exist() {

        return companyRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() {

        try {
           return Files.readString(Path.of(COMPANIES_FILE_PATH_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void importToDb(ImportCompaniesInputStringDto companiesInput) {

        CompanyRootDto companyRootDto = xmlParser.deserialize(companiesInput.getCompanies(), CompanyRootDto.class);

        companyRootDto.getCompanies()
                .stream()
                .map(companyDto -> modelMapper.map(companyDto, Company.class))
                .forEach(companyRepository::save);
    }

    @Override
    public Company findCompanyByName(String name) {
        return companyRepository.findByName(name);
    }
}
