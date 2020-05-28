package com.shop.controllers;

import com.shop.dao.StoreRepository;
import com.shop.entities.Store;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/stores")
public class StoreController {
    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    @PostMapping(path = "/add")
    public void addStore(@RequestBody Store store) {
        storeRepository.save(store);
    }

    @GetMapping(path = "/{id}")
    public Optional<Store> findStoreById(@PathVariable(name = "id") Long id) {
        return storeRepository.findById(id);
    }

    @PutMapping(path = "/update")
    public void updateStore(@RequestBody Store store) {
        storeRepository.save(store);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteStore(@PathVariable(name = "id") Long id) {
        storeRepository.deleteById(id);
    }
}
