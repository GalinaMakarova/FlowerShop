package com.shop.services;

import com.shop.dao.CountryRepository;
import com.shop.entities.Country;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CountryServiceImpl implements DaoService<Country> {
    private final CountryRepository countryRepository;
    private static final Logger log = Logger.getLogger(CountryServiceImpl.class.getName());

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = countryRepository.findAll();
        log.info("COUNTRY: number of items found - " + countries.size());
        return countries;
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country add(Country country) {
        List<Country> countries = findAll();
        if (!countries.contains(country)) {
            log.info("Country added: " + country.toString());
            return countryRepository.save(country);
        } else {
            log.warning("WARNING: " + country.toString() + " is already in the repository");
            return null;
        }
    }

    @Override
    public boolean update(Country country) {
        Optional<Country> countryFromDB = countryRepository.findById(country.getId());
        if (countryFromDB.isPresent()) {
            log.info("Country updated: " + country.toString());
            countryRepository.save(country);
            return true;
        } else {
            log.warning("WARNING: " + country.toString() + " is not found!");
            return false;
        }
    }

    @Override
    public void deleteById(Long id) {
        String bufStr = countryRepository.findById(id).toString();
        Optional<Country> countryFromDB = countryRepository.findById(id);
        if (countryFromDB.isPresent()) {
            countryRepository.deleteById(id);
            log.info("Country removed: " + bufStr);
        } else {
            log.warning("WARNING: Country with ID=" + id + " is not found!");
        }
    }

    @Override
    public void delete(Country country) {
        String bufStr = country.toString();
        Optional<Country> countryFromDB = countryRepository.findById(country.getId());
        if (countryFromDB.isPresent()) {
            countryRepository.delete(country);
            log.info("Country removed: " + bufStr);
        } else {
            log.warning("WARNING: " + country.toString() + " is not found!");
        }

    }
}
