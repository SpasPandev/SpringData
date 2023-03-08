package com.example.workshop_mvc_project.controller;

import com.example.workshop_mvc_project.service.EmployeeService;
import com.example.workshop_mvc_project.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController extends BaseController{

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/export/project-if-finished")
    public String finishedProject(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("projectsIfFinished", projectService.exportAllFinishedProjects());

        return "export/export-project-if-finished";
    }

    @GetMapping("/export/employees-above")
    public String employeesAbove(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("employeesAbove", employeeService.exportAllEmployeesAbove25());

        return "export/export-employees-with-age";
    }
}
