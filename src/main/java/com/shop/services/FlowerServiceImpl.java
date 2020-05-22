package com.shop.services;

import com.shop.dao.FlowerRepository;
import com.shop.entities.Flower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FlowerServiceImpl implements DaoService<Flower> {
    @Autowired
    private final FlowerRepository flowerRepository;
    private static final Logger log = Logger.getLogger(FlowerServiceImpl.class.getName());

    public FlowerServiceImpl(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    @Override
    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }

    @Override
    public Flower findById(Long id) {
        return flowerRepository.findById(id);
    }

    @Override
    public void add(Flower flower) {
        List<Flower> flowers = findAll();
        if (!flowers.contains(flower)) {
            flowerRepository.add(flower);
            log.info("Flower added: " + flower.toString());
        } else {
            log.info("WARNING: " + flower.toString() + " is already in the repository");
        }
    }

    @Override
    public void update(Flower flower) {
        flowerRepository.update(flower);
        log.info("Flower updated: " + flower.toString());
    }

    @Override
    public void delete(Long id) {
        String bufStr = flowerRepository.findById(id).toString();
        flowerRepository.delete(id);
        log.info("Flower removed: " + bufStr);
    }
}
