package com.shop.dao;

import com.shop.entities.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class EmployeeRepository implements DaoCRUD<Employee> {
    private final EntityManagerFactory entityManagerFactory;

    public EmployeeRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public Set<Employee> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return new HashSet<>(em.createQuery("FROM Employee", Employee.class).getResultList());
    }

    @Transactional
    @Override
    public Employee findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Employee result = em.createQuery("From Employee as c where c.id= ?1", Employee.class)
                .setParameter(1, id)
                .getResultList().get(0);
        em.close();
        return result;
    }

    @Transactional
    @Override
    public void add(Employee employee) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(employee);
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }

    @Transactional
    @Override
    public void delete(Employee employee) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Employee WHERE id=:id").setParameter("id", employee.getId()).executeUpdate();
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }
}

