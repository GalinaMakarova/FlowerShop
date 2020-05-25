package com.shop.controllers;

import com.shop.dao.DaoCRUD;
import com.shop.entities.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final DaoCRUD<Employee> employeeDAO;

    public EmployeeController(@Qualifier("employeeDAO") DaoCRUD<Employee> employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping
    public Collection<Employee> getEmployees() {
        return employeeDAO.findAll();
    }

    @PostMapping(path = "/add")
    public void addEmployee(@RequestBody Employee employee) {
        employeeDAO.add(employee);
    }

    @GetMapping(path = "/{id}")
    public Employee findEmployeeById(@PathVariable(name = "id") Long id) {
        return employeeDAO.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeDAO.update(employee);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteEmployee(@PathVariable(name = "id") Long id) {
        employeeDAO.delete(id);
    }
}
