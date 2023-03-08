package com.example.workshop_mvc_project.controller;

import com.example.workshop_mvc_project.service.CompanyService;
import com.example.workshop_mvc_project.service.EmployeeService;
import com.example.workshop_mvc_project.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController{

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public HomeController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        if (this.isLoggedIn(request)) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {

        if (!this.isLoggedIn(request)) {
            return "redirect:/";
        }

        model.addAttribute("areImported", companyService.exist() &&
                projectService.exist() && employeeService.exist());

        return "home";
    }
}
