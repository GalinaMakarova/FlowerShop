package repositories;

import dao.DaoCRUD;
import entities.Flower;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import utils.SessionUtil;
import entities.Country;

import java.util.HashSet;
import java.util.Set;

public class CountryRepository extends SessionUtil implements DaoCRUD<Country> {
    @Override
    public Set<Country> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Country").list());
    }

    @Override
    public Country findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Country.class, id);
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
            session.delete(flower);
            transaction.commit();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        }
        session.delete(country);
        transaction.commit();
        session.close();
    }
}

