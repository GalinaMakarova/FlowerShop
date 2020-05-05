package repositories;

import dao.DaoCRUD;
import entities.Flower;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import entities.Country;

import java.util.HashSet;
import java.util.Set;

public class CountryRepository implements DaoCRUD<Country> {
    @Override
    public Set<Country> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Country").list());
    }

    public Set<Country> findAllFromDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Country> countries = new HashSet<>(session.createNamedQuery("Country.findAll", Country.class).getResultList());
        session.close();
        return countries;
    }

    @Override
    public Country findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Country.class, id);
    }

    public Country findByIdFromDB(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Country country = session.createNamedQuery("Country.findById", Country.class).setParameter("id", id).getSingleResult();
        session.close();
        return country;
    }

    @Override
    public void add(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(country);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(country);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Set<Flower> flowers = country.getFlowers();
        for (Flower flower : flowers) {
            flower.deleteCountry(country);
            session.update(flower);
            transaction.commit();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        }
        session.delete(country);
        transaction.commit();
        session.close();
    }
}

