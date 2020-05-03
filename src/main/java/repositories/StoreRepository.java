package repositories;

import dao.DaoCRUD;
import org.hibernate.Transaction;
import org.hibernate.Session;
import utils.HibernateUtil;
import utils.SessionUtil;
import entities.Store;

import java.util.HashSet;
import java.util.Set;

public class StoreRepository extends SessionUtil implements DaoCRUD<Store> {
    @Override
    public Set<Store> findAll() {
        return new HashSet<>(HibernateUtil.getSessionFactory().openSession().createQuery("From Store").list());
    }

    @Override
    public Store findById(Long id) {
        return HibernateUtil.getSessionFactory().openSession().get(Store.class, id);
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

