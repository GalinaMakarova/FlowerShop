package com.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.TestBaseConfig;
import com.shop.entities.Country;
import com.shop.services.DaoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestBaseConfig.class,
        CountryController.class
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountryControllerTest {
    private MockMvc mvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    DaoService<Country> countryService;

    static Country country1 = new Country(RandomStringUtils.randomAlphabetic(7));
    static Country country2 = new Country(RandomStringUtils.randomAlphabetic(5));

    @BeforeAll
    void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        em.getTransaction().begin();
        em.persist(country1);
        em.persist(country2);
        em.getTransaction().commit();
    }

    @AfterAll
    void cleanUp() {
        em.close();
        entityManagerFactory.close();
    }

    @SneakyThrows
    @Test
    void getCountries() {
        mvc.perform(MockMvcRequestBuilders
                .get("/countries")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void addCountry() {
        Country country = new Country(RandomStringUtils.randomAlphabetic(6));
        String json = new ObjectMapper().writeValueAsString(country);
        mvc.perform(MockMvcRequestBuilders
                .post("/countries/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findCountryById() {
        Long id = 1L;
        mvc.perform(MockMvcRequestBuilders
                .get("/countries/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @SneakyThrows
    @Test
    void updateCountry() {
        Long id = 1L;
        String newName = RandomStringUtils.randomAlphabetic(4);
        ObjectMapper mapper = new ObjectMapper();
        Country country = countryService.findById(id);
        country.setName(newName);
        String json = mapper.writeValueAsString(country);
        mvc.perform(MockMvcRequestBuilders
                .post("/countries/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/countries/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName));
    }

    @SneakyThrows
    @Test
    void deleteCountry() {
        Long id = 2L;
        mvc.perform(MockMvcRequestBuilders
                .get("/countries/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}