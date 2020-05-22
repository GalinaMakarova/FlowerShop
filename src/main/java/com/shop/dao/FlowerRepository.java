package com.shop.dao;

import com.shop.entities.Flower;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class FlowerRepository implements DaoCRUD<Flower> {
    EntityManager entityManager;

    public FlowerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Flower> findAll() {
        return entityManager.createQuery("FROM Flower", Flower.class).getResultList();
    }

    @Override
    public Flower findById(Long id) {
        return entityManager.createQuery("FROM Flower AS f WHERE f.id= ?1", Flower.class)
                .setParameter(1, id)
                .getResultList().get(0);
    }

    @Override
    public void add(Flower flower) {
        entityManager.getTransaction().begin();
        entityManager.persist(flower);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Flower flower) {
        entityManager.getTransaction().begin();
        entityManager.merge(flower);
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
        entityManager.createQuery("DELETE FROM Flower WHERE id=:id").setParameter("id", id).executeUpdate();
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException exception) {
            entityManager.getTransaction().rollback();
            exception.printStackTrace();
        }
    }
}

