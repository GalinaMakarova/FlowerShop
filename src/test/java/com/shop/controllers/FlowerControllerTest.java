package com.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entities.Flower;
import com.shop.services.DaoService;
import com.shop.TestBaseConfig;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestBaseConfig.class,
        FlowerController.class
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlowerControllerTest {
    private MockMvc mvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    DaoService<Flower> flowerService;

    static Flower flower1 = new Flower(RandomStringUtils.randomAlphabetic(7));
    static Flower flower2 = new Flower(RandomStringUtils.randomAlphabetic(5));

    @AfterAll
    void cleanUp() {
        em.close();
        entityManagerFactory.close();
    }


    @BeforeAll
    void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        em.getTransaction().begin();
        em.persist(flower1);
        em.persist(flower2);
        em.getTransaction().commit();
    }


    @SneakyThrows
    @Test
    void getFlowers() {
        mvc.perform(MockMvcRequestBuilders
                .get("/flowers")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void addFlower() {
        Flower flower = new Flower(RandomStringUtils.randomAlphabetic(4));
        String json = new ObjectMapper().writeValueAsString(flower);
        mvc.perform(MockMvcRequestBuilders
                .post("/flowers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findFlowerById() {
        Long id = 1L;
        mvc.perform(MockMvcRequestBuilders
                .get("/flowers/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @SneakyThrows
    @Test
    void updateFlower() {
        Long id = 1L;
        String newName = RandomStringUtils.randomAlphabetic(6);
        ObjectMapper mapper = new ObjectMapper();
        Flower flower = flowerService.findById(id);
        flower.setName(newName);
        String json = mapper.writeValueAsString(flower);
        mvc.perform(MockMvcRequestBuilders
                .post("/flowers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/flowers/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName));
    }

    @SneakyThrows
    @Test
    void deleteFlower() {
        Long id = 2L;
        mvc.perform(MockMvcRequestBuilders
                .get("/flowers/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}