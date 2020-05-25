package com.shop.controllers;

import com.shop.dao.DaoCRUD;
import com.shop.entities.Flower;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/flowers")
public class FlowerController {
    private final DaoCRUD<Flower> flowerDAO;

    public FlowerController(@Qualifier("flowerDAO") DaoCRUD<Flower> flowerDAO) {
        this.flowerDAO = flowerDAO;
    }

    @GetMapping
    public Collection<Flower> getFlowers() {
        return flowerDAO.findAll();
    }

    @PostMapping(path = "/add")
    public void addFlower(@RequestBody Flower flower) {
        flowerDAO.add(flower);
    }

    @GetMapping(path = "/{id}")
    public Flower findFlowerById(@PathVariable(name = "id") Long id) {
        return flowerDAO.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateFlower(@RequestBody Flower flower) {
        flowerDAO.update(flower);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteFlower(@PathVariable(name = "id") Long id) {
        flowerDAO.delete(id);
    }
}