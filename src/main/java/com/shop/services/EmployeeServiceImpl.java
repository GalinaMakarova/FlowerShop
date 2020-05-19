package com.shop.services;

import com.shop.dao.EmployeeRepository;
import com.shop.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.logging.Logger;

@Service
public class EmployeeServiceImpl implements DaoService<Employee> {
    private final EmployeeRepository employeeRepository;
    private static Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Set<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void add(Employee employee) {
        employeeRepository.add(employee);
        log.info("Employee added: " + employee.toString());
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
        log.info("Employee updated: " + employee.toString());
    }

    @Override
    public void delete(Employee employee) {
        String bufStr = employee.toString();
        employeeRepository.delete(employee);
        log.info("Employee removed: " + bufStr);
    }
}
