package com.shop.services;

import com.shop.dao.CountryRepository;
import com.shop.entities.Country;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.shop.SpringcoreApplication.class)
@TestPropertySource("classpath:test.properties")
class CountryServiceImplTest {

    @Autowired
    CountryServiceImpl countryService;

    @MockBean
    CountryRepository countryRepository;

    Country country1 = new Country(1L, RandomStringUtils.randomAlphabetic(5));
    Country country2 = new Country(2L, RandomStringUtils.randomAlphabetic(7));

    @Test
    void findAll() {
        doReturn(List.of(country1, country2)).when(countryRepository).findAll();
        List<Country> getAllCompanies = countryService.findAll();
        Assert.assertEquals(List.of(country1, country2), getAllCompanies);
        Mockito.verify(countryRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        doReturn(Optional.of(country1)).when(countryRepository).findById(anyLong());
        Optional<Country> countryFromDB = countryService.findById(1L);
        if (countryFromDB.isPresent()) {
            Assert.assertEquals(country1, countryFromDB.get());
            Mockito.verify(countryRepository, times(1)).findById(country1.getId());
        }
    }

    @Test
    void add() {
        doReturn(country1).when(countryRepository).save(any(Country.class));
        Country newCountry = countryService.add(country1);
        Assert.assertEquals(country1, newCountry);
        Mockito.verify(countryRepository, times(1)).save(country1);
    }

    @Test
    void update() {
        doReturn(Optional.of(country1)).when(countryRepository).findById(anyLong());
        country1.setName(country1.getName() + RandomStringUtils.randomAlphabetic(5));
        boolean methodReturnTrueIfCompanyWasSuccessfullySaved = countryService.update(country1);
        Assert.assertTrue(methodReturnTrueIfCompanyWasSuccessfullySaved);
        Mockito.verify(countryRepository, times(1)).findById(country1.getId());
        Mockito.verify(countryRepository, times(1)).save(country1);
    }

    @Test
    void deleteById() {
        doNothing().when(countryRepository).deleteById(anyLong());
        doReturn(Optional.of(country2)).when(countryRepository).findById(anyLong());
        countryService.deleteById(country2.getId());
        Mockito.verify(countryRepository, times(1)).deleteById(country2.getId());
        Mockito.verify(countryRepository, times(2)).findById(country2.getId());
    }

    @Test
    void delete() {
        doNothing().when(countryRepository).delete(any(Country.class));
        doReturn(Optional.of(country2)).when(countryRepository).findById(anyLong());
        countryService.delete(country2);
        Mockito.verify(countryRepository, times(1)).delete(country2);
        Mockito.verify(countryRepository, times(1)).findById(country2.getId());
    }
}