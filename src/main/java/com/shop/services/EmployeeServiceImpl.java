package com.shop.services;

import com.shop.dao.EmployeeRepository;
import com.shop.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmployeeServiceImpl implements DaoService<Employee> {
    private final EmployeeRepository employeeRepository;
    private static final Logger log = Logger.getLogger(EmployeeServiceImpl.class.getName());

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void add(Employee employee) {
        List<Employee> employees = findAll();
        if (!employees.contains(employee)) {
            employeeRepository.add(employee);
            log.info("Employee added: " + employee.toString());
        } else {
            String name = employee.getName();
            try {
                int i = Integer.parseInt(name.substring(name.length() - 1));
                i = i + 1;
                name = name.substring(0, name.length() - 1) + i;
            } catch (Exception e) {
                name = name + 1;
            }
            employee.setName(name);
            employeeRepository.add(employee);
            log.info("Employee copy added: " + employee.toString());
        }
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
        log.info("Employee updated: " + employee.toString());
    }

    @Override
    public void delete(Long id) {
        String bufStr = employeeRepository.findById(id).toString();
        employeeRepository.delete(id);
        log.info("Employee removed: " + bufStr);
    }
}
