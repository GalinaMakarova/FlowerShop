package repositories;

import entities.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class CountryRepositoryTest {
    static CountryRepository countryRepository;

    @BeforeAll
    static void init() {
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
        //comparing all countries from repository and from DataBase
        assertEquals(countryRepository.findAll().size(), countryRepository.findAllFromDB().size());
    }

    @Test
    void findAll() {
        //comparing all countries from repository and from DataBase
        assertEquals(countryRepository.findAll().size(), countryRepository.findAllFromDB().size());
    }

    @Test
    void findById() {
        //creation new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);
        //comparing country found from repository and country found from DataBase
        assertEquals(countryRepository.findById(ru.getId()), countryRepository.findByIdFromDB(ru.getId()));
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
        //comparing country found from repository and country found from DataBase
        assertEquals(countryRepository.findById(ru.getId()), countryRepository.findByIdFromDB(ru.getId()));
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
        assertEquals(countryRepository.findAll().size(), countryRepository.findAllFromDB().size());

        //deleting the Country and changing countries count to -1
        countryRepository.delete(ru);
        count = count - 1;
        //checking that current count of countries equals to "count" value
        assertEquals(count, countryRepository.findAll().size());
        //comparing Country found from repository and from DataBase
        assertEquals(countryRepository.findAll().size(), countryRepository.findAllFromDB().size());
    }
}