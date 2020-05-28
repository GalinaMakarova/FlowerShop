package com.shop.dao;

import com.shop.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
//    EntityManager entityManager;
//
//    public CountryRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public List<Country> findAll() {
//        return entityManager.createQuery("FROM Country", Country.class).getResultList();
//    }
//
//    @Override
//    public Country findById(Long id) {
//        return entityManager.createQuery("FROM Country AS c WHERE c.id= ?1", Country.class)
//                .setParameter(1, id)
//                .getResultList().get(0);
//    }
//
//    @Override
//    public void add(Country country) {
//        entityManager.getTransaction().begin();
//        entityManager.persist(country);
//        try {
//            entityManager.getTransaction().commit();
//        } catch (RollbackException exception) {
//            entityManager.getTransaction().rollback();
//            exception.printStackTrace();
//        }
//    }
//
//    @Override
//    public void update(Country country) {
//        entityManager.getTransaction().begin();
//        entityManager.merge(country);
//        try {
//            entityManager.getTransaction().commit();
//        } catch (RollbackException exception) {
//            entityManager.getTransaction().rollback();
//            exception.printStackTrace();
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        entityManager.getTransaction().begin();
//        entityManager.createQuery("DELETE FROM Country WHERE id=:id").setParameter("id", id).executeUpdate();
//        try {
//            entityManager.getTransaction().commit();
//        } catch (RollbackException exception) {
//            entityManager.getTransaction().rollback();
//            exception.printStackTrace();
//        }
//    }
}

