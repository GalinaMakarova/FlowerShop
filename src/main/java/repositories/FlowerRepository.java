package repositories;

import dao.DaoCRUD;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import utils.SessionUtil;
import entities.Country;
import entities.Flower;

import java.util.HashSet;
import java.util.Set;

public class FlowerRepository extends SessionUtil implements DaoCRUD<Flower> {

    @Override
    public Set<Flower> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Flower").list());
    }

    @Override
    public Flower findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Flower.class, id);
    }

    @Override
    public void add(Flower flower) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(flower);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Flower flower) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(flower);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Flower flower) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Set<Country> countries = flower.getCountries();
        for (Country country : countries) {
            country.deleteFlower(flower);
            session.update(country);
            transaction.commit();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        }
        session.delete(flower);
        transaction.commit();
        session.close();
    }
}

