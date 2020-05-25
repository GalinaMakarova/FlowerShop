package com.shop.services;

import com.shop.dao.StoreRepository;
import com.shop.entities.Store;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Store findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public void add(Store store) {
        storeRepository.add(store);
        log.info("Store added: " + store.toString());
    }

    @Override
    public void update(Store store) {
        storeRepository.update(store);
        log.info("Store updated: " + store.toString());
    }

    @Override
    public void delete(Long id) {
        String bufStr = storeRepository.findById(id).toString();
        storeRepository.delete(id);
        log.info("Store removed: " + bufStr);
    }
}
