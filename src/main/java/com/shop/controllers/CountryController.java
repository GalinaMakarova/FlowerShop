package com.shop.controllers;

import com.shop.dao.CountryRepository;
import com.shop.dao.DaoCRUD;
import com.shop.entities.Country;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(@Qualifier("countryRepository") DaoCRUD<Country> countryDao) {
        this.countryRepository = (CountryRepository) countryDao;
    }

    @GetMapping
    public Set<Country> getCountries() {
        return countryRepository.findAll();
    }

    @PostMapping
    public void addCountry(@RequestBody Country country) {
        System.out.println("----- added country from country controller: " + country);
    }

    @GetMapping(path = "/{id}")
//    @ResponseBody
    public Country findCountryById(@PathVariable(name = "id") Long id) {
        return countryRepository.findById(id);
    }

    @PostMapping
    public void updateCountry(@RequestBody Country country) {
        System.out.println("----- updated country from country controller: " + country);
    }

    @PostMapping
    public void deleteCountry(@RequestBody Country country) {
        System.out.println("----- deleted country from country controller: " + country);
    }
}
