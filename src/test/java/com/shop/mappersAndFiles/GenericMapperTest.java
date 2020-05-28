package com.shop.mappersAndFiles;

import com.shop.entities.Country;
import com.shop.entities.Employee;
import com.shop.entities.Flower;
import com.shop.entities.Store;
import com.shop.services.DaoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.shop.SpringcoreApplication.class)
@TestPropertySource("classpath:test.properties")
public class GenericMapperTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    EntityManager entityManager;

    @Autowired
    GenericMapper<Flower> mapper;

    @Autowired
    DaoService<Country> companyServiceDAO;

    @Test
    public void writeToJsonMapperTest() {
        GenericMapper<Flower> mapper = new GenericMapper<>();
        EntityManager em = entityManagerFactory.createEntityManager();

        Country country1 = new Country(RandomStringUtils.randomAlphabetic(7));
        Country country2 = new Country(RandomStringUtils.randomAlphabetic(4));

        Employee employee1 = new Employee(RandomStringUtils.randomAlphabetic(7));
        Employee employee2 = new Employee(RandomStringUtils.randomAlphabetic(4));

        Store store1 = new Store(RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(5));
        store1.setEmployee(employee1);

        Store store2 = new Store(RandomStringUtils.randomAlphabetic(4), RandomStringUtils.randomAlphabetic(8));
        store2.setEmployee(employee2);

        Flower flower1 = new Flower(RandomStringUtils.randomAlphabetic(5));
        Flower flower2 = new Flower(RandomStringUtils.randomAlphabetic(7));

        flower1.setCountryList(Collections.singletonList(country1));
        flower2.setCountryList(Collections.singletonList(country2));
        flower1.setStore(store1);
        flower2.setStore(store2);

        em.getTransaction().begin();
        em.persist(flower1);
        em.persist(flower2);
        em.getTransaction().commit();
        em.close();

        em = entityManagerFactory.createEntityManager();

        flower1 = em.createQuery("FROM Flower f WHERE f.id=:id", Flower.class).setParameter("id", 1L).getSingleResult();
        flower2 = em.createQuery("FROM Flower f WHERE f.id=:id", Flower.class).setParameter("id", 2L).getSingleResult();

        em.close();
        mapper.mapListToJson(List.of(flower1, flower2));

        File outputFile = new File("outputFile.json");
        assertNotNull(outputFile);
    }


    @Test
    public void readFromJsonMapperTest() {
        GenericMapper<Flower> mapper = new GenericMapper<>();
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Flower> list = mapper.mapJsonToList(Flower.class);
        list.forEach(System.out::println);

        for (Flower flower : list) {
            em.getTransaction().begin();
            em.persist(flower);
            em.getTransaction().commit();
        }
        em.close();
    }
}
