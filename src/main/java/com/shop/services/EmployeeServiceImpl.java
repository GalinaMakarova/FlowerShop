package com.shop.services;

import com.shop.dao.EmployeeRepository;
import com.shop.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee add(Employee employee) {
        List<Employee> employees = findAll();
        if (!employees.contains(employee)) {
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
            log.info("Employee copy added: " + employee.toString());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public boolean update(Employee employee) {
        Optional<Employee> employeeFromDB = employeeRepository.findById(employee.getId());
        if (employeeFromDB.isPresent()) {
            employeeRepository.save(employee);
            log.info("Employee updated: " + employee.toString());
            return true;
        } else {
            log.warning("WARNING: " + employee.toString() + " is not found!");
            return false;
        }
    }

    @Override
    public void deleteById(Long id) {
        String bufStr = employeeRepository.findById(id).toString();
        Optional<Employee> employeeFromDB = employeeRepository.findById(id);
        if (employeeFromDB.isPresent()) {
            employeeRepository.deleteById(id);
            log.info("Employee removed: " + bufStr);
        } else {
            log.warning("WARNING: Employee with ID=" + id + " is not found!");
        }
    }

    @Override
    public void delete(Employee employee) {
        String bufStr = employee.toString();
        Optional<Employee> employeeFromDB = employeeRepository.findById(employee.getId());
        if (employeeFromDB.isPresent()) {
            employeeRepository.delete(employee);
            log.info("Employee removed: " + bufStr);
        } else {
            log.warning("WARNING: " + employee.toString() + " is not found!");
        }
    }
}
