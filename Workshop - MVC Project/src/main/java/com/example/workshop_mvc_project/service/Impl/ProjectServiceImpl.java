package com.example.workshop_mvc_project.service.Impl;

import com.example.workshop_mvc_project.model.dto.ExportedProjectDto;
import com.example.workshop_mvc_project.model.dto.ImportProjectsInputStringDto;
import com.example.workshop_mvc_project.model.dto.ProjectRootDto;
import com.example.workshop_mvc_project.model.entity.Project;
import com.example.workshop_mvc_project.repository.ProjectRepository;
import com.example.workshop_mvc_project.service.CompanyService;
import com.example.workshop_mvc_project.service.ProjectService;
import com.example.workshop_mvc_project.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final String PROJECTS_FILE_NAME_PATH = "src/main/resources/files/xmls/projects.xml";
    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyService companyService, ModelMapper modelMapper, XmlParser xmlParser) {
        this.projectRepository = projectRepository;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean exist() {

        return projectRepository.existsAllBy();
    }

    @Override
    public String getXmlForImport() {

        try {

            return Files.readString(Path.of(PROJECTS_FILE_NAME_PATH));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importToDb(ImportProjectsInputStringDto projectsInput) {

        ProjectRootDto projectRootDto = xmlParser.deserialize(projectsInput.getProjects(),
                ProjectRootDto.class);

        projectRootDto.getProjects()
                .stream()
                .map(projectDto -> {
                    Project project = modelMapper.map(projectDto, Project.class);

                    project.setCompany(companyService.findCompanyByName(project.getCompany().getName()));

                    return project;
                })
                .forEach(projectRepository::save);

    }

    @Override
    public Project findProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public Object exportAllFinishedProjects() {

        StringBuilder stringBuilder = new StringBuilder();

        projectRepository.findAllByFinishedIsTrue()
                .stream()
                .map(project -> modelMapper.map(project, ExportedProjectDto.class))
                .forEach(exportedProjectDto -> stringBuilder
                        .append(getInfoToString(exportedProjectDto))
                        .append(System.lineSeparator()));

        return stringBuilder;
    }

    private String getInfoToString(ExportedProjectDto exportedProjectDto) {

        return "Project Name: " + exportedProjectDto.getName() + "\n" +
                "\tDescription: " + exportedProjectDto.getDescription() + "\n" +
                "\t" + exportedProjectDto.getPayment();
    }
}
