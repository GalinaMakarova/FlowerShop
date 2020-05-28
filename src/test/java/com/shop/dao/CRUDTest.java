package com.shop.dao;

import com.shop.entities.Country;
import com.shop.entities.Employee;
import com.shop.entities.Flower;
import com.shop.entities.Store;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.shop.SpringcoreApplication.class)
@TestPropertySource("classpath:test.properties")
class CRUDTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void crudAllEntitiesAddTest() {
        EntityManager em = entityManagerFactory.createEntityManager();

        Country country1 = new Country(RandomStringUtils.randomAlphabetic(7));
        Country country2 = new Country(RandomStringUtils.randomAlphabetic(7));

        Employee employee1 = new Employee(RandomStringUtils.randomAlphabetic(12));

        Store store1 = new Store(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(20));
        store1.setEmployee(employee1);

        Flower flower1 = new Flower(RandomStringUtils.randomAlphabetic(7));
        Flower flower2 = new Flower(RandomStringUtils.randomAlphabetic(7));

        flower1.setCountryList(Collections.singletonList(country1));
        flower2.setCountryList(Arrays.asList(country1, country2));
        flower1.setStore(store1);
        flower2.setStore(store1);

        em.getTransaction().begin();
        em.persist(flower1);
        em.persist(flower2);
        em.getTransaction().commit();

        int countriesCount = em.createQuery("FROM Country", Country.class).getResultList().size();
        int flowersCount = em.createQuery("FROM Flower", Flower.class).getResultList().size();
        int employeeCount = em.createQuery("FROM Employee", Employee.class).getResultList().size();
        int storeCount = em.createQuery("FROM Store", Store.class).getResultList().size();

        em.close();

        assertEquals(countriesCount, 2);
        assertEquals(flowersCount, 2);
        assertEquals(employeeCount, 1);
        assertEquals(storeCount, 1);
    }
}