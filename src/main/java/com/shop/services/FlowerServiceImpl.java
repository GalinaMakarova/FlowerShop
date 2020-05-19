package com.shop.services;

import com.shop.dao.FlowerRepository;
import com.shop.entities.Flower;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.logging.Logger;

@Service
public class FlowerServiceImpl implements DaoService<Flower> {
    private final FlowerRepository flowerRepository;
    private static Logger log = Logger.getLogger(FlowerServiceImpl.class.getName());

    public FlowerServiceImpl(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    @Override
    public Set<Flower> findAll() {
        return flowerRepository.findAll();
    }

    @Override
    public Flower findById(Long id) {
        return flowerRepository.findById(id);
    }

    @Override
    public void add(Flower flower) {
        flowerRepository.add(flower);
        log.info("Flower added: " + flower.toString());
    }

    @Override
    public void update(Flower flower) {
        flowerRepository.update(flower);
        log.info("Flower updated: " + flower.toString());
    }

    @Override
    public void delete(Flower flower) {
        String bufStr = flower.toString();
        flowerRepository.delete(flower);
        log.info("Flower removed: " + bufStr);
    }
}
