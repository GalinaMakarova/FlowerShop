package repositories;

import dao.DaoCRUD;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import entities.Store;

import java.util.HashSet;
import java.util.Set;

public class StoreRepository implements DaoCRUD<Store> {
    @Override
    public Set<Store> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Store").list());
    }

    public Set<Store> findAllFromDB() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Set<Store> stores = new HashSet<>(session.createNamedQuery("Store.findAll", Store.class).getResultList());
        session.close();
        return stores;
    }

    @Override
    public Store findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Store.class, id);
    }

    public Store findByIdFromDB(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Store store = session.createNamedQuery("Store.findById", Store.class).setParameter("id", id).getSingleResult();
        session.close();
        return store;
    }

    @Override
    public void add(Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(store);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(store);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Store store) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(store);
        transaction.commit();
        session.close();
    }
}

