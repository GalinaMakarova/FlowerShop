package com.shop.testConfigs;

import com.shop.dao.*;
import com.shop.entities.*;
import com.shop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class TestBaseConfig {
    @Autowired
    private Environment env;

    @Bean
    public EntityManager em() {
        return Objects.requireNonNull(entityManagerFactory().getObject()).createEntityManager();
    }

    @Bean
    public DaoCRUD<Country> countryDAO(EntityManager em) {
        return new CountryRepository(em);
    }

    @Bean
    public DaoService<Country> countryService(DaoCRUD<Country> countryDAO) {
        return new CountryServiceImpl((CountryRepository) countryDAO);
    }

    @Bean
    public DaoCRUD<Flower> flowerDAO(EntityManager em) {
        return new FlowerRepository(em);
    }

    @Bean
    public DaoService<Flower> flowerService(DaoCRUD<Flower> flowerDAO) {
        return new FlowerServiceImpl((FlowerRepository) flowerDAO);
    }

    @Bean
    public DaoCRUD<Employee> employeeDAO(EntityManager em) {
        return new EmployeeRepository(em);
    }

    @Bean
    public DaoService<Employee> employeeService(DaoCRUD<Employee> employeeDAO) {
        return new EmployeeServiceImpl((EmployeeRepository) employeeDAO);
    }

    @Bean
    public DaoCRUD<Store> storeDAO(EntityManager em) {
        return new StoreRepository(em);
    }

    @Bean
    public DaoService<Store> storeService(DaoCRUD<Store> storeDAO) {
        return new StoreServiceImpl((StoreRepository) storeDAO);
    }




    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setPackagesToScan("com.shop");
        entityManagerFactory.setJpaProperties(hibernateJpaProperties());
        return entityManagerFactory;
    }

    private Properties hibernateJpaProperties() {
        Properties props = new Properties();
        props.put(org.hibernate.cfg.Environment.DRIVER, env.getProperty("spring.datasource.driverClassName"));
        props.put(org.hibernate.cfg.Environment.URL, env.getProperty("spring.datasource.url"));
        props.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
        props.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
        props.put(org.hibernate.cfg.Environment.USER, env.getProperty("spring.datasource.username"));
        props.put(org.hibernate.cfg.Environment.PASS, env.getProperty("spring.datasource.password"));
        return props;
    }
}