package repositories;

import dao.DaoCRUD;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import entities.Country;
import entities.Flower;

import java.util.HashSet;
import java.util.Set;

public class FlowerRepository implements DaoCRUD<Flower> {

    @Override
    public Set<Flower> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Flower").list());
    }

    public Set<Flower> findAllFromDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Flower> flowers = new HashSet<>(session.createNamedQuery("Flower.findAll", Flower.class).getResultList());
        session.close();
        return flowers;
    }

    @Override
    public Flower findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Flower.class, id);
    }

    public Flower findByIdFromDB(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Flower flower = session.createNamedQuery("Flower.findById", Flower.class).setParameter("id", id).getSingleResult();
        session.close();
        return flower;
    }

    @Override
    public void add(Flower flower) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(flower);
        transaction.commit();
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

