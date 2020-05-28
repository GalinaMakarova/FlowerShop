package com.shop.controllers;

import com.shop.entities.Country;
import com.shop.services.DaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {
    private final DaoService<Country> countryService;

    public CountryController(DaoService<Country> countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getCountries() {
        return countryService.findAll();
    }

    @PostMapping(path = "/add")
    public void addCountry(@RequestBody Country country) {
        countryService.add(country);
    }

    @GetMapping(path = "/{id}")
    public Optional<Country> findCountryById(@PathVariable(name = "id") Long id) {
        return countryService.findById(id);
    }

    @PostMapping(path = "/update")
    public void updateCountry(@RequestBody Country country) {
        countryService.update(country);
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteCountry(@PathVariable(name = "id") Long id) {
        countryService.deleteById(id);
    }
}
