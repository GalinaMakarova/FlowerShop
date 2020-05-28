package com.shop.controllers;

import com.shop.dao.FlowerRepository;
import com.shop.entities.Flower;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/flowers")
public class FlowerController {
    private final FlowerRepository flowerRepository;

    public FlowerController(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    @GetMapping
    public List<Flower> getFlowers() {
        return flowerRepository.findAll();
    }

    @PostMapping(path = "/add")
    public void addFlower(@RequestBody Flower flower) {
        flowerRepository.save(flower);
    }

    @GetMapping(path = "/{id}")
    public Optional<Flower> findFlowerById(@PathVariable(name = "id") Long id) {
        return flowerRepository.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateFlower(@RequestBody Flower flower) {
        flowerRepository.save(flower);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteFlower(@PathVariable(name = "id") Long id) {
        flowerRepository.deleteById(id);
    }
}