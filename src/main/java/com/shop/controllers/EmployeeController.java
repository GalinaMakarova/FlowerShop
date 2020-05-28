package com.shop.controllers;

import com.shop.dao.EmployeeRepository;
import com.shop.entities.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping(path = "/add")
    public void addEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping(path = "/{id}")
    public Optional<Employee> findEmployeeById(@PathVariable(name = "id") Long id) {
        return employeeRepository.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteEmployee(@PathVariable(name = "id") Long id) {
        employeeRepository.deleteById(id);
    }
}
