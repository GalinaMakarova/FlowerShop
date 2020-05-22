package com.shop.dao;

import com.shop.entities.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class EmployeeRepository implements DaoCRUD<Employee> {
    EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        return entityManager.createQuery("FROM Employee", Employee.class).getResultList();
    }

    @Override
    public Employee findById(Long id) {
        return entityManager.createQuery("FROM Employee AS e WHERE e.id= ?1", Employee.class)
                .setParameter(1, id)
                .getResultList().get(0);
    }

    @Override
    public void add(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.merge(employee);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Employee WHERE id=:id").setParameter("id", id).executeUpdate();
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}

