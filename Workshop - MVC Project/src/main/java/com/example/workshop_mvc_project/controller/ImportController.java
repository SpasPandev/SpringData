package com.example.workshop_mvc_project.controller;

import com.example.workshop_mvc_project.model.dto.ImportCompaniesInputStringDto;
import com.example.workshop_mvc_project.model.dto.ImportEmployeesInputStringDto;
import com.example.workshop_mvc_project.model.dto.ImportProjectsInputStringDto;
import com.example.workshop_mvc_project.service.CompanyService;
import com.example.workshop_mvc_project.service.EmployeeService;
import com.example.workshop_mvc_project.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ImportController extends  BaseController{

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ImportController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public String importXml(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {

            return "redirect:/";
        }

        model.addAttribute("areImported", new boolean[]{
                companyService.exist(), projectService.exist(), employeeService.exist()
        });

        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String importCompanies(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("companies", companyService.getXmlForImport());

        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String importCompanies(ImportCompaniesInputStringDto companies) {

        companyService.importToDb(companies);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String importProjects(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("projects", projectService.getXmlForImport());

        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjects(ImportProjectsInputStringDto projects) {

        projectService.importToDb(projects);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String importEmployees(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("employees", employeeService.getXmlForImport());

        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployees(ImportEmployeesInputStringDto employees) {

        employeeService.importToDb(employees);

        return "redirect:/import/xml";
    }
}
