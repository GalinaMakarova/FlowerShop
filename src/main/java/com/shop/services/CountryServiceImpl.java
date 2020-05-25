package com.shop.services;

import com.shop.dao.CountryRepository;
import com.shop.dao.DaoCRUD;
import com.shop.entities.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CountryServiceImpl implements DaoService<Country> {
    private final DaoCRUD<Country> countryDAO;
    private static final Logger log = Logger.getLogger(CountryServiceImpl.class.getName());

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryDAO = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = countryDAO.findAll();
        log.info("COUNTRY: number of items found - " + countries.size());
        return countries;
    }

    @Override
    public Country findById(Long id) {
        return countryDAO.findById(id);
    }

    @Override
    public void add(Country country) throws Exception {
        List<Country> countries = findAll();
        if (!countries.contains(country)) {
            countryDAO.add(country);
            log.info("Country added: " + country.toString());
        } else {
            log.warning("WARNING: " + country.toString() + " is already in the repository");
            throw new Exception("The object is already in the repository");
        }

    }

    @Override
    public void update(Country country) {
        countryDAO.update(country);
        log.info("Country updated: " + country.toString());
    }

    @Override
    public void delete(Long id) {
        String bufStr = countryDAO.findById(id).toString();
        countryDAO.delete(id);
        log.info("Country removed: " + bufStr);
    }
}
