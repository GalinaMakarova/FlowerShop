package com.shop.dao;

import com.shop.entities.Store;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class StoreRepository implements DaoCRUD<Store> {
    private final EntityManagerFactory entityManagerFactory;

    public StoreRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public Set<Store> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return new HashSet<>(em.createQuery("FROM Store", Store.class).getResultList());
    }

    @Transactional
    @Override
    public Store findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Store result = em
                .createQuery("From Store as e where e.id= ?1", Store.class)
                .setParameter(1, id)
                .getResultList().get(0);
        em.close();
        return result;
    }

    @Transactional
    @Override
    public void add(Store store) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(store);
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
    public void update(Store store) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(store);
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
    public void delete(Store store) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Store WHERE id=:id").setParameter("id", store.getId()).executeUpdate();
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }
}

