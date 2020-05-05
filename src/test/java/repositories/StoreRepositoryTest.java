package repositories;

import entities.Store;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class StoreRepositoryTest {
    static StoreRepository storeRepository;

    @BeforeAll
    static void init() {
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
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), storeRepository.findAllFromDB().size());
    }

    @Test
    void findAll() {
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), storeRepository.findAllFromDB().size());
    }

    @Test
    void findById() {
        //saving a new one
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);
        //comparing Store found from repository and from DataBase
        assertEquals(storeRepository.findById(store.getId()), storeRepository.findByIdFromDB(store.getId()));
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
        //comparing Store found from repository and from DataBase
        assertEquals(storeRepository.findById(store.getId()), storeRepository.findByIdFromDB(store.getId()));
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
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), storeRepository.findAllFromDB().size());

        //deleting the Store and changing stores count to -1
        storeRepository.delete(store);
        count = count - 1;
        //checking that current count of stores equals to "count" value
        assertEquals(count, storeRepository.findAll().size());
        //comparing all stores from repository and from DataBase
        assertEquals(storeRepository.findAll().size(), storeRepository.findAllFromDB().size());
    }
}