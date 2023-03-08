package com.example.workshop_mvc_project.service;

import com.example.workshop_mvc_project.model.dto.ImportProjectsInputStringDto;
import com.example.workshop_mvc_project.model.entity.Project;

public interface ProjectService {
    boolean exist();

    String getXmlForImport();

    void importToDb(ImportProjectsInputStringDto projectsInput);

    Project findProjectByName(String name);

    Object exportAllFinishedProjects();
}
