package repositories;

import entities.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class EmployeeRepositoryTest {
    static EmployeeRepository employeeRepository;

    @BeforeAll
    static void init() {
        employeeRepository = new EmployeeRepository();
    }

    //new Employee creation without any association validations
    @Test
    void addEmployeeOnly() {
        //getting current count of Employees
        int count = employeeRepository.findAll().size();
        //saving a new one
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);
        //checking that Employees count has been changed to count+1
        assertEquals(count + 1, employeeRepository.findAll().size());
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), employeeRepository.findAllFromDB().size());
    }

    @Test
    void findAll() {
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), employeeRepository.findAllFromDB().size());
    }

    @Test
    void findById() {
        //saving a new one
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findById(employee.getId()), employeeRepository.findByIdFromDB(employee.getId()));
    }

    @Test
    void updateEmployeeOnly() {
        //saving a new one
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);
        //updating Employee.name and saving changes
        employeeName = "Dr.Who";
        employee.setName(employeeName);
        employeeRepository.update(employee);
        //comparing Employee.name found from repository and from DataBase
        assertEquals(employeeRepository.findById(employee.getId()), employeeRepository.findByIdFromDB(employee.getId()));
    }

    @Test
    void deleteEmployeeOnly() {
        //getting current count of Employees
        int count = employeeRepository.findAll().size();
        //saving a new one
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);
        //changed Employees count to +1
        count = count + 1;
        //checking that count+1 is current count of Employees
        assertEquals(count, employeeRepository.findAll().size());
        //comparing Employee found from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), employeeRepository.findAllFromDB().size());

        //deleting the Employee and changing Employees count to -1
        employeeRepository.delete(employee);
        count = count - 1;
        //checking that current count of Employees equals to "count" value
        assertEquals(count, employeeRepository.findAll().size());
        //comparing Employee found from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), employeeRepository.findAllFromDB().size());
    }
}