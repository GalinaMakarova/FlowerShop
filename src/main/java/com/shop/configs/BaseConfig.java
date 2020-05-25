package com.shop.configs;

import com.shop.dao.*;
import com.shop.entities.Country;
import com.shop.entities.Employee;
import com.shop.entities.Flower;
import com.shop.entities.Store;
import com.shop.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;

@Configuration
public class BaseConfig {
    private Environment environment;

    public BaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DaoCRUD<Country> countryDAO(EntityManager entityManager) {
        return new CountryRepository(entityManager);
    }

    @Bean
    public DaoService<Country> countryService(DaoCRUD<Country> countryDAO) {
        return new CountryServiceImpl((CountryRepository) countryDAO);
    }

    @Bean
    public DaoCRUD<Employee> employeeDAO(EntityManager entityManager) {
        return new EmployeeRepository(entityManager);
    }

    @Bean
    public DaoService<Employee> employeeService(DaoCRUD<Employee> employeeDAO) {
        return new EmployeeServiceImpl((EmployeeRepository) employeeDAO);
    }

    @Bean
    public DaoCRUD<Flower> flowerDAO(EntityManager entityManager) {
        return new FlowerRepository(entityManager);
    }

    @Bean
    public DaoService<Flower> flowerService(DaoCRUD<Flower> flowerDAO) {
        return new FlowerServiceImpl((FlowerRepository) flowerDAO);
    }

    @Bean
    public DaoCRUD<Store> storeDAO(EntityManager entityManager) {
        return new StoreRepository(entityManager);
    }

    @Bean
    public DaoService<Store> storeService(DaoCRUD<Store> storeDAO) {
        return new StoreServiceImpl((StoreRepository) storeDAO);
    }
}
