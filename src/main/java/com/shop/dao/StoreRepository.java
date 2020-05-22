package com.shop.dao;

import com.shop.entities.Store;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class StoreRepository implements DaoCRUD<Store> {
    EntityManager entityManager;

    public StoreRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Store> findAll() {
        return entityManager.createQuery("FROM Store", Store.class).getResultList();
    }

    @Override
    public Store findById(Long id) {
        return entityManager
                .createQuery("FROM Store AS s WHERE s.id= ?1", Store.class)
                .setParameter(1, id)
                .getResultList().get(0);
    }

    @Override
    public void add(Store store) {
        entityManager.getTransaction().begin();
        entityManager.persist(store);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Store store) {
        entityManager.getTransaction().begin();
        entityManager.merge(store);
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
        entityManager.createQuery("DELETE FROM Store WHERE id=:id").setParameter("id", id).executeUpdate();
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}

