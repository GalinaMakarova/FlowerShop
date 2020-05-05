package repositories;

import dao.DaoCRUD;
import entities.Store;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import entities.Employee;

import java.util.HashSet;
import java.util.Set;

public class EmployeeRepository implements DaoCRUD<Employee> {
    @Override
    public Set<Employee> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Employee").list());
    }

    public Set<Employee> findAllFromDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Employee> employees = new HashSet<>(session.createNamedQuery("Employee.findAll", Employee.class).getResultList());
        session.close();
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Employee.class, id);
    }

    public Employee findByIdFromDB(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.createNamedQuery("Employee.findById", Employee.class).setParameter("id", id).getSingleResult();
        session.close();
        return employee;
    }

    @Override
    public void add(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Store store = employee.getStore();
        if (store != null) {
            store.setEmployee(null);
            session.update(store);
            transaction.commit();

            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        }
        session.remove(employee);
        transaction.commit();
        session.close();
    }
}

