package com.shop.controllers;

import com.shop.dao.DaoCRUD;
import com.shop.entities.Store;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/stores")
public class StoreController {
    private final DaoCRUD<Store> storeDAO;

    public StoreController(@Qualifier("storeDAO") DaoCRUD<Store> storeDAO) {
        this.storeDAO = storeDAO;
    }

    @GetMapping
    public Collection<Store> getStores() {
        return storeDAO.findAll();
    }

    @PostMapping(path = "/add")
    public void addStore(@RequestBody Store store) {
        storeDAO.add(store);
    }

    @GetMapping(path = "/{id}")
    public Store findStoreById(@PathVariable(name = "id") Long id) {
        return storeDAO.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateStore(@RequestBody Store store) {
        storeDAO.update(store);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteStore(@PathVariable(name = "id") Long id) {
        storeDAO.delete(id);
    }
}
