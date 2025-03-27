package com.company.salary_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.company.salary_management.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.company.salary_management.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/create")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        try {
            employeeService.saveEmployee(employee);
            return "redirect:/employees"; // Chuyển hướng đến danh sách nhân viên
        } catch (IllegalArgumentException e) {
            return "error"; // Trả về trang báo lỗi nếu email đã tồn tại
        }
    }


    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employee-form";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        if (updatedEmployee != null) {
            return "redirect:/employees";
        }
        return "error"; // Hiển thị trang lỗi nếu không cập nhật được
    }


    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
    @GetMapping("/search")
    public String searchEmployees(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("employees", employeeService.searchEmployees(keyword));
        } else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }
        model.addAttribute("keyword", keyword);
        return "employees/search";
    }
}

