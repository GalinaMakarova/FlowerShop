package com.shop.dao;

import com.shop.entities.Country;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CountryRepository implements DaoCRUD<Country> {
    private final EntityManagerFactory entityManagerFactory;

    public CountryRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public Set<Country> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return new HashSet<>(em.createQuery("FROM Country", Country.class).getResultList());
    }

    @Transactional
    @Override
    public Country findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Country result = em.createQuery("From Country as c where c.id= ?1", Country.class)
                .setParameter(1, id)
                .getResultList().get(0);
        em.close();
        return result;
    }

    @Transactional
    @Override
    public void add(Country country) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(country);
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
    public void update(Country country) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(country);
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
    public void delete(Country country) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Country WHERE id=:id").setParameter("id", country.getId()).executeUpdate();
        try {
            em.getTransaction().commit();
        } catch (RollbackException exception) {
            em.getTransaction().rollback();
            exception.printStackTrace();
        }
        em.close();
    }
}

