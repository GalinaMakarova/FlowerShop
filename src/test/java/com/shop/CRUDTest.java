package com.shop;

import com.shop.entities.Country;
import com.shop.entities.Employee;
import com.shop.entities.Flower;
import com.shop.entities.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestBaseConfig.class
})
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CRUDTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void crudAllEntitiesAddTest() {
        EntityManager em = entityManagerFactory.createEntityManager();

        Country country1 = new Country();
        country1.setName("Russia");
        Country country2 = new Country();
        country2.setName("England");

        Employee employee1 = new Employee();
        employee1.setName("Nobody");

        Store store1 = new Store();
        store1.setName("SPAR");
        store1.setAddress("SomeCity, SomeStreet#1");
        store1.setEmployee(employee1);

        Flower flower1 = new Flower();
        flower1.setName("Rose");
        Flower flower2 = new Flower();
        flower2.setName("Tulip");

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