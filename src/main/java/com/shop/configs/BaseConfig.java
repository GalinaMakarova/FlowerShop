package com.shop.configs;

import com.shop.dao.CountryRepository;
import com.shop.dao.DaoCRUD;
import com.shop.entities.Country;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManagerFactory;

@Configuration
public class BaseConfig {
    private Environment environment;

    public BaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DaoCRUD<Country> countryDAO(EntityManagerFactory entityManagerFactory) {
        return new CountryRepository(entityManagerFactory);
    }

}
