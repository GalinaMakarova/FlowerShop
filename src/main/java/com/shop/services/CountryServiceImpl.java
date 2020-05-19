package com.shop.services;

import com.shop.dao.CountryRepository;
import com.shop.entities.Country;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.logging.Logger;

@Service
public class CountryServiceImpl implements DaoService<Country> {
    private final CountryRepository countryRepository;
    private static Logger log = Logger.getLogger(CountryServiceImpl.class.getName());

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Set<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public void add(Country country) {
        countryRepository.add(country);
        log.info("Country added: " + country.toString());
    }

    @Override
    public void update(Country country) {
        countryRepository.update(country);
        log.info("Country updated: " + country.toString());
    }

    @Override
    public void delete(Country country) {
        String bufStr = country.toString();
        countryRepository.delete(country);
        log.info("Country removed: " + bufStr);
    }
}
