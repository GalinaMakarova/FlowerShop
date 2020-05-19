package com.shop.dao;

import com.shop.entities.Flower;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class FlowerRepository implements DaoCRUD<Flower> {
    private final EntityManagerFactory entityManagerFactory;

    public FlowerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public Set<Flower> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return new HashSet<>(em.createQuery("FROM Flower", Flower.class).getResultList());
    }

    @Transactional
    @Override
    public Flower findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Flower result = em.createQuery("From Flower as e where e.id= ?1", Flower.class)
                .setParameter(1, id)
                .getResultList().get(0);
        em.close();
        return result;
    }

    @Transactional
    @Override
    public void add(Flower flower) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(flower);
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
    public void update(Flower flower) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(flower);
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
    public void delete(Flower flower) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Flower WHERE id=:id").setParameter("id", flower.getId()).executeUpdate();
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }
}

