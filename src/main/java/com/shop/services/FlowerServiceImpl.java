package com.shop.services;

import com.shop.dao.FlowerRepository;
import com.shop.entities.Flower;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FlowerServiceImpl implements DaoService<Flower> {
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
    public Optional<Flower> findById(Long id) {
        return flowerRepository.findById(id);
    }

    @Override
    public void add(Flower flower) {
        List<Flower> flowers = findAll();
        if (!flowers.contains(flower)) {
            flowerRepository.save(flower);
            log.info("Flower added: " + flower.toString());
        } else {
            log.info("WARNING: " + flower.toString() + " is already in the repository");
        }
    }

    @Override
    public void update(Flower flower) {
        flowerRepository.save(flower);
        log.info("Flower updated: " + flower.toString());
    }

    @Override
    public void deleteById(Long id) {
        String bufStr = flowerRepository.findById(id).toString();
        Optional<Flower> flowerFromDB = flowerRepository.findById(id);
        if (flowerFromDB.isPresent()) {
            flowerRepository.deleteById(id);
            log.info("flower removed: " + bufStr);
        } else {
            log.warning("WARNING: flower with ID=" + id + " is not found!");
        }
    }

    @Override
    public void delete(Flower flower) {
        String bufStr = flower.toString();
        Optional<Flower> flowerFromDB = flowerRepository.findById(flower.getId());
        if (flowerFromDB.isPresent()) {
            flowerRepository.delete(flower);
            log.info("flower removed: " + bufStr);
        } else {
            log.warning("WARNING: " + flower.toString() + " is not found!");
        }
    }
}
