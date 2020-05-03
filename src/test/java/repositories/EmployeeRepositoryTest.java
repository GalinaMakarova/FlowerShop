package repositories;

import entities.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DBConnection;
import utils.HibernateUtil;

import static org.junit.Assert.assertEquals;

class EmployeeRepositoryTest {
    static DBConnection dbConnection;
    static EmployeeRepository employeeRepository;

    @BeforeAll
    static void init() {
        dbConnection = new DBConnection();
        dbConnection.getConnection();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), session.createNamedQuery("Employee.findAll", Employee.class).getResultList().size());
        session.close();
    }

    @Test
    void findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findAll().size(), session.createNamedQuery("Employee.findAll", Employee.class).getResultList().size());
        session.close();
    }

    @Test
    void findById() {
        //saving a new one
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all Employees from repository and from DataBase
        assertEquals(employeeRepository.findById(employee.getId()), session.createNamedQuery("Employee.findById", Employee.class).setParameter("id", employee.getId()).getSingleResult());
        session.close();
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
        Long id = employee.getId();
        //comparing Employee.name found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(employeeRepository.findById(id).getName(), session.createNamedQuery("Employee.findById", Employee.class).setParameter("id", employee.getId()).getSingleResult().getName());
        session.close();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(employeeRepository.findAll().size(), session.createNamedQuery("Employee.findAll", Employee.class).getResultList().size());
        session.close();

        //deleting the Employee and changing Employees count to -1
        employeeRepository.delete(employee);
        count = count - 1;
        //checking that current count of Employees equals to "count" value
        assertEquals(count, employeeRepository.findAll().size());
        //comparing Employee found from repository and from DataBase
        session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(employeeRepository.findAll().size(), session.createNamedQuery("Employee.findAll", Employee.class).getResultList().size());
        session.close();
    }
}