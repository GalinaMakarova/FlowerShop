package repositories;

import entities.Country;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DBConnection;
import utils.HibernateUtil;

import static org.junit.Assert.assertEquals;

class CountryRepositoryTest {
    static DBConnection dbConnection;
    static CountryRepository countryRepository;

    @BeforeAll
    static void init() {
        dbConnection = new DBConnection();
        dbConnection.getConnection();
        countryRepository = new CountryRepository();
    }

    //new country creation without any association validations
    @Test
    void addCountryOnly() {
        //getting current count of countries
        int count = countryRepository.findAll().size();
        //saving a new one
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);
        //checking that countries count has been changed to count+1
        assertEquals(count + 1, countryRepository.findAll().size());
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all countries from repository and from DataBase
        assertEquals(countryRepository.findAll().size(), session.createNamedQuery("Country.findAll", Country.class).getResultList().size());
        session.close();
    }

    @Test
    void findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all countries from repository and from DataBase
        assertEquals(countryRepository.findAll().size(), session.createNamedQuery("Country.findAll", Country.class).getResultList().size());
        session.close();
    }

    @Test
    void findById() {
        //creation new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);
        //comparing country found from repository and country found from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(countryRepository.findById(ru.getId()), session.createNamedQuery("Country.findById", Country.class).setParameter("id", ru.getId()).getSingleResult());
        session.close();
    }

    @Test
    void updateCountryOnly() {
        //creation new Country
        String countryName = "England";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);
        //updating Country.name and saving changes
        countryName = "Russia";
        ru.setName(countryName);
        countryRepository.update(ru);
        Long id = ru.getId();
        //comparing country found from repository and country found from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(countryRepository.findById(id).getName(), session.createNamedQuery("Country.findById", Country.class).setParameter("id", ru.getId()).getSingleResult().getName());
        session.close();
    }

    @Test
    void deleteCountryOnly() {
        //getting current count of countries
        int count = countryRepository.findAll().size();
        //creation new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);
        //changed country count to +1
        count = count + 1;
        //checking that count+1 is current count of countries
        assertEquals(count, countryRepository.findAll().size());
        //comparing Country found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(countryRepository.findAll().size(), session.createNamedQuery("Country.findAll", Country.class).getResultList().size());
        session.close();

        //deleting the Country and changing countries count to -1
        countryRepository.delete(ru);
        count = count - 1;
        //checking that current count of countries equals to "count" value
        assertEquals(count, countryRepository.findAll().size());
        //comparing Country found from repository and from DataBase
        session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(countryRepository.findAll().size(), session.createNamedQuery("Country.findAll", Country.class).getResultList().size());
        session.close();
    }
}