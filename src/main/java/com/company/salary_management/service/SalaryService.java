package com.company.salary_management.service;

import org.springframework.stereotype.Service;

@Service
public class SalaryService {
    public double calculateSalary(double baseSalary, double bonus, double deductions) {
        return baseSalary + bonus - deductions;
    }
}