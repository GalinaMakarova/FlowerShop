package repositories;

import entities.Store;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DBConnection;
import utils.HibernateUtil;

import static org.junit.Assert.assertEquals;

class StoreRepositoryTest {
    static DBConnection dbConnection;
    static StoreRepository storeRepository;

    @BeforeAll
    static void init() {
        dbConnection = new DBConnection();
        dbConnection.getConnection();
        storeRepository = new StoreRepository();
    }

    //new Store creation without any association validations
    @Test
    void addStoreOnly() {
        //getting current count of Stores
        int count = storeRepository.findAll().size();
        //saving a new one
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);
        //checking that stores count has been changed to count+1
        assertEquals(count + 1, storeRepository.findAll().size());
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), session.createNamedQuery("Store.findAll", Store.class).getResultList().size());
        session.close();
    }

    @Test
    void findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), session.createNamedQuery("Store.findAll", Store.class).getResultList().size());
        session.close();
    }

    @Test
    void findById() {
        //saving a new one
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);
        //comparing Store found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(storeRepository.findById(store.getId()), session.createNamedQuery("Store.findById", Store.class).setParameter("id", store.getId()).getSingleResult());
        session.close();
    }

    @Test
    void updateStoreOnly() {
        //saving a new one
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);
        //updating Store.name and saving changes
        storeName = "Auchan";
        store.setName(storeName);
        storeRepository.update(store);
        Long id = store.getId();
        //comparing Store found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(storeRepository.findById(store.getId()).getName(), session.createNamedQuery("Store.findById", Store.class).setParameter("id", store.getId()).getSingleResult().getName());
        session.close();
    }

    @Test
    void deleteStoreOnly() {
        //getting current count of stores
        int count = storeRepository.findAll().size();
        //saving a new one
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);
        //changed stores count to +1
        count = count + 1;
        //checking that count+1 is current count of stores
        assertEquals(count, storeRepository.findAll().size());
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), session.createNamedQuery("Store.findAll", Store.class).getResultList().size());
        session.close();

        //deleting the Store and changing stores count to -1
        storeRepository.delete(store);
        count = count - 1;
        //checking that current count of stores equals to "count" value
        assertEquals(count, storeRepository.findAll().size());
        session = HibernateUtil.getSessionFactory().openSession();
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), session.createNamedQuery("Store.findAll", Store.class).getResultList().size());
        session.close();
    }
}