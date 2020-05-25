package com.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entities.Store;
import com.shop.services.DaoService;
import com.shop.TestBaseConfig;
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
        StoreController.class
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreControllerTest {
    private MockMvc mvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    DaoService<Store> storeService;

    static Store store1 = new Store(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(4));
    static Store store2 = new Store(RandomStringUtils.randomAlphabetic(5));

    @AfterAll
    void cleanUp() {
        em.close();
        entityManagerFactory.close();
    }


    @BeforeAll
    void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        em.getTransaction().begin();
        em.persist(store1);
        em.persist(store2);
        em.getTransaction().commit();
    }

    @Test
    void getStores() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/stores")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addStore() throws Exception {
        Store store = new Store(RandomStringUtils.randomAlphabetic(4), RandomStringUtils.randomAlphabetic(4));
        String json = new ObjectMapper().writeValueAsString(store);
        mvc.perform(MockMvcRequestBuilders
                .post("/stores/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findStoreById() throws Exception {
        Long id = 1L;
        mvc.perform(MockMvcRequestBuilders
                .get("/stores/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void updateStore() throws Exception {
        Long id = 1L;
        String newName = RandomStringUtils.randomAlphabetic(8);
        ObjectMapper mapper = new ObjectMapper();
        Store store = storeService.findById(id);
        store.setName(newName);
        String json = mapper.writeValueAsString(store);
        mvc.perform(MockMvcRequestBuilders
                .post("/stores/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/stores/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    void deleteStore() throws Exception {
        Long id = 2L;
        mvc.perform(MockMvcRequestBuilders
                .get("/stores/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}