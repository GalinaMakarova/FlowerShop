package com.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entities.Country;
import com.shop.services.CountryServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.shop.SpringcoreApplication.class)
@AutoConfigureMockMvc
class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CountryServiceImpl countryService;

    Country country1 = new Country(RandomStringUtils.randomAlphabetic(5));
    Country country2 = new Country(RandomStringUtils.randomAlphabetic(7));

    @Test
    void getCountries() throws Exception {
        List<Country> countries = List.of(country1, country2);
        when(countryService.findAll()).thenReturn(countries);
        mvc.perform(MockMvcRequestBuilders.get("/countries")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addCountry() throws Exception {
        when(countryService.add(any(Country.class))).thenReturn(country1);
        mvc.perform(MockMvcRequestBuilders.post("/countries/add")
                .content(new ObjectMapper().writeValueAsString(country1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findCountryById() throws Exception {
        when(countryService.findById(anyLong())).thenReturn(Optional.ofNullable(country1));
        mvc.perform(MockMvcRequestBuilders.get("/countries/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(country1.getName()));
    }

    @Test
    void updateCountry() throws Exception {
        when(countryService.update(any(Country.class))).thenReturn(true);
        country1.setName(country1.getName() + "_UPDATE");
        mvc.perform(MockMvcRequestBuilders.put("/countries/update")
                .content(new ObjectMapper().writeValueAsString(country1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteCountryById() throws Exception {
        doNothing().when(countryService).deleteById(anyLong());
        mvc.perform(MockMvcRequestBuilders.delete("/countries/delete/{id}", 1)
                .content(new ObjectMapper().writeValueAsString(country2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteCountry() throws Exception {
        doNothing().when(countryService).deleteById(anyLong());
        mvc.perform(MockMvcRequestBuilders.delete("/countries/delete")
                .content(new ObjectMapper().writeValueAsString(country2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void noResultExceptionHandler() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/countries/test")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }
}