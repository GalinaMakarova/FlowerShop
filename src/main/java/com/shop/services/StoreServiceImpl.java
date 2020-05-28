package com.shop.services;

import com.shop.dao.StoreRepository;
import com.shop.entities.Store;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StoreServiceImpl implements DaoService<Store> {
    private final StoreRepository storeRepository;
    private static final Logger log = Logger.getLogger(StoreServiceImpl.class.getName());

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public void add(Store store) {
        storeRepository.save(store);
        log.info("Store added: " + store.toString());
    }

    @Override
    public void update(Store store) {
        storeRepository.save(store);
        log.info("Store updated: " + store.toString());
    }

    @Override
    public void deleteById(Long id) {
        String bufStr = storeRepository.findById(id).toString();
        Optional<Store> storeFromDB = storeRepository.findById(id);
        if (storeFromDB.isPresent()) {
            storeRepository.deleteById(id);
            log.info("Store removed: " + bufStr);
        } else {
            log.warning("WARNING: Store with ID=" + id + " is not found!");
        }
    }

    @Override
    public void delete(Store store) {
        String bufStr = store.toString();
        Optional<Store> storeFromDB = storeRepository.findById(store.getId());
        if (storeFromDB.isPresent()) {
            storeRepository.delete(store);
            log.info("Store removed: " + bufStr);
        } else {
            log.warning("WARNING: " + store.toString() + " is not found!");
        }
    }
}
