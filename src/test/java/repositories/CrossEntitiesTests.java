package repositories;

import entities.Country;
import entities.Employee;
import entities.Flower;
import entities.Store;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class CrossEntitiesTests {

    static CountryRepository countryRepository;
    static FlowerRepository flowerRepository;
    static StoreRepository storeRepository;
    static EmployeeRepository employeeRepository;

    @BeforeAll
    static void init() {
        countryRepository = new CountryRepository();
        flowerRepository = new FlowerRepository();
        storeRepository = new StoreRepository();
        employeeRepository = new EmployeeRepository();
    }

    @Test
    void addCountryFlower_UpdateFromFlower() {
        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);

        //saving their association
        flower.addCountry(ru);
        flowerRepository.update(flower);

        //checking that country got flower to its flowerList from DB and repository sides
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().size(), 1);
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().size(),
                countryRepository.findById(ru.getId()).getFlowers().size());

        //checking that flower got country to its countryList from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().size(), 1);
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().size(),
                flowerRepository.findById(flower.getId()).getCountries().size());
    }

    @Test
    void addCountryFlower_UpdateFromCountry() {
        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);

        //saving their association
        ru.addFlower(flower);
        flowerRepository.update(flower);
        countryRepository.update(ru);

        //checking that country got flower to its flowerList from DB and repository sides
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().size(), 1);
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().size(),
                countryRepository.findByIdFromDB(ru.getId()).getFlowers().size());

        //checking that flower got country to its countryList from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().size(), 1);
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().size(),
                flowerRepository.findByIdFromDB(flower.getId()).getCountries().size());
    }

    @Test
    void addFlowerStore_UpdateFromFlower() {
        //UpdateFromStore case is not real since Flower object cannot exist with fk_store = null
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //checking that store got flower to its flowerList from DB and repository sides
        assertEquals(storeRepository.findById(store.getId()).getFlowers().size(), 1);
        assertEquals(storeRepository.findById(store.getId()).getFlowers().size(),
                storeRepository.findByIdFromDB(store.getId()).getFlowers().size());

        //checking that flower got store to its fk_store from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getStore(), store);
        assertEquals(flowerRepository.findById(flower.getId()).getStore(),
                flowerRepository.findByIdFromDB(flower.getId()).getStore());
    }

    @Test
    void addStoreEmployee_UpdateFromStore() {
        //UpdateFromEmployee case is not real since Store contains fk_employee so Store should be updated anyway
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Employee
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);

        //saving their association
        store.setEmployee(employee);
        storeRepository.update(store);

        //checking that store got employee to its employee link from DB and repository sides
        assertEquals(storeRepository.findById(store.getId()).getEmployee(), employee);
        assertEquals(storeRepository.findById(store.getId()).getEmployee(),
                storeRepository.findByIdFromDB(store.getId()).getEmployee());

        //checking that employee got store to its fk_store from DB and repository sides
        assertEquals(employeeRepository.findById(employee.getId()).getStore(), store);
        assertEquals(employeeRepository.findById(employee.getId()).getStore(),
                employeeRepository.findByIdFromDB(employee.getId()).getStore());
    }

    @Test
    void updateEmployee_StoreCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Employee
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);

        //saving their association
        store.setEmployee(employee);
        storeRepository.update(store);

        //employee updating
        employeeName = "Dr.Who";
        employee.setName(employeeName);
        employeeRepository.update(employee);

        //checking that employee got updated name from DB and repository sides
        assertEquals(employeeRepository.findById(employee.getId()).getName(), employeeName);
        assertEquals(employeeRepository.findById(employee.getId()).getName(),
                employeeRepository.findByIdFromDB(employee.getId()).getName());

        //checking that store contains updated employee
        assertEquals(storeRepository.findById(store.getId()).getEmployee().getName(), employeeName);
        assertEquals(storeRepository.findById(store.getId()).getEmployee().getName(),
                storeRepository.findByIdFromDB(store.getId()).getEmployee().getName());
    }

    @Test
    void updateStore_EmployeeAndFlowerCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //saving a new Employee
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);

        //saving their association
        store.setEmployee(employee);
        storeRepository.update(store);

        //store updating
        storeName = "Auchan";
        store.setName(storeName);
        storeRepository.update(store);

        //checking that store got updated name from DB and repository sides
        assertEquals(storeRepository.findById(store.getId()).getName(), storeName);
        assertEquals(storeRepository.findById(store.getId()).getName(),
                storeRepository.findByIdFromDB(store.getId()).getName());

        //checking that employee contains updated store from DB and repository sides
        assertEquals(employeeRepository.findById(employee.getId()).getStore().getName(), storeName);
        assertEquals(employeeRepository.findById(employee.getId()).getStore().getName(),
                employeeRepository.findByIdFromDB(employee.getId()).getStore().getName());

        //checking that flower contains updated store from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getStore().getName(), storeName);
        assertEquals(flowerRepository.findById(flower.getId()).getStore().getName(),
                flowerRepository.findByIdFromDB(flower.getId()).getStore().getName());
    }

    @Test
    void updateFlower_StoreAndCountryCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving their association
        ru.addFlower(flower);
        flowerRepository.update(flower);
        countryRepository.update(ru);

        //flower updating
        flowerName = "Gerbera";
        flower.setName(flowerName);
        flowerRepository.update(flower);

        //getting updated store
        store = storeRepository.findByIdFromDB(store.getId());

        //checking that flower got updated name from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getName(), flowerName);
        assertEquals(flowerRepository.findById(flower.getId()).getName(),
                flowerRepository.findByIdFromDB(flower.getId()).getName());

        //checking that store contains updated flower from DB and repository sides
        assertEquals(storeRepository.findById(store.getId()).getFlowers().stream().
                filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName(), flowerName);
        assertEquals(storeRepository.findById(store.getId()).getFlowers().stream().
                        filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName(),
                storeRepository.findByIdFromDB(store.getId()).getFlowers().stream().
                        filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName());

        //checking that country contains updated flower from DB and repository sides
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().stream().
                filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName(), flowerName);
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().stream().
                        filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName(),
                countryRepository.findByIdFromDB(ru.getId()).getFlowers().stream().
                        filter(data -> Objects.equals(data.getName(), "Gerbera")).findFirst().get().getName());
    }


    @Test
    void updateCountry_FlowerCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving their association
        ru.addFlower(flower);
        flowerRepository.update(flower);
        countryRepository.update(ru);

        //country updating
        countryName = "Russian Federation";
        ru.setName(countryName);
        countryRepository.update(ru);

        //getting updated flower
        flower = flowerRepository.findByIdFromDB(flower.getId());

        //checking that country got updated name from DB and repository sides
        assertEquals(countryRepository.findById(ru.getId()).getName(), countryName);
        assertEquals(countryRepository.findById(ru.getId()).getName(),
                countryRepository.findByIdFromDB(ru.getId()).getName());

        //checking that flower contains updated country from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().stream().
                filter(data -> Objects.equals(data.getName(), "Russian Federation")).findFirst().get().getName(), countryName);
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().stream().
                        filter(data -> Objects.equals(data.getName(), "Russian Federation")).findFirst().get().getName(),
                flowerRepository.findByIdFromDB(flower.getId()).getCountries().stream().
                        filter(data -> Objects.equals(data.getName(), "Russian Federation")).findFirst().get().getName());
    }

    @Test
    void deleteEmployee_StoreCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Employee
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);

        //saving their association
        store.setEmployee(employee);
        storeRepository.update(store);

        //saving current employees count from DB and repository sides
        int employeeCount = employeeRepository.findAll().size() - 1;
        int employeeCountFromDB = employeeRepository.findAllFromDB().size() - 1;
        assertEquals(employeeCount, employeeCountFromDB);

        //employee deleting
        employeeRepository.delete(employee);

        //checking that employee removed from DB and repository sides
        assertEquals(employeeRepository.findAll().size(), employeeCount);
        assertEquals(employeeRepository.findAllFromDB().size(), employeeCountFromDB);

        //checking that store exist from DB and repository sides
        assertNotNull(storeRepository.findById(store.getId()));
        assertNotNull(storeRepository.findByIdFromDB(store.getId()));

        //checking that Store.Employee = Null from DB and repository sides
        assertNull(storeRepository.findById(store.getId()).getEmployee());
        assertNull(storeRepository.findByIdFromDB(store.getId()).getEmployee());
    }

    @Test
    void deleteStore_FlowerAndEmployeeCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Employee
        String employeeName = "Mr.Nobody";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.add(employee);

        //saving their association
        store.setEmployee(employee);
        storeRepository.update(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //getting updated store with flower
        store = storeRepository.findByIdFromDB(store.getId());

        //saving current stores count from DB and repository sides
        int storeCount = storeRepository.findAll().size() - 1;
        int storeCountFromDB = storeRepository.findAllFromDB().size() - 1;
        assertEquals(storeCount, storeCountFromDB);

        //saving current flowers count from DB and repository sides
        int flowerCount = flowerRepository.findAll().size() - 1;
        int flowerCountFromDB = flowerRepository.findAllFromDB().size() - 1;
        assertEquals(flowerCount, flowerCountFromDB);

        //store deleting
        storeRepository.delete(store);

        //checking that store removed from DB and repository sides
        assertEquals(storeRepository.findAll().size(), storeCount);
        assertEquals(storeRepository.findAllFromDB().size(), storeCountFromDB);

        //checking that employee exist from DB and repository sides
        assertNotNull(employeeRepository.findById(employee.getId()));
        assertNotNull(employeeRepository.findByIdFromDB(employee.getId()));

        //checking that all flower is removed from DB and repository sides
        assertEquals(flowerRepository.findAll().size(), flowerCount);
        assertEquals(flowerRepository.findAllFromDB().size(), flowerCountFromDB);
    }

    @Test
    void deleteFlower_StoreAndCountryCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving their association
        ru.addFlower(flower);
        flowerRepository.update(flower);
        countryRepository.update(ru);

        //saving current flowers count from DB and repository sides
        int flowerCount = flowerRepository.findAll().size() - 1;
        int flowerCountFromDB = flowerRepository.findAllFromDB().size() - 1;
        assertEquals(flowerCount, flowerCountFromDB);

        //saving current flowers count for Store from DB and repository sides
        int countForStore = storeRepository.findById(store.getId()).getFlowers().size() - 1;
        int countForStoreFromDB = storeRepository.findByIdFromDB(store.getId()).getFlowers().size() - 1;
        assertEquals(countForStore, countForStoreFromDB);

        //saving current flowers count for Country from DB and repository sides
        int countForCountry = countryRepository.findById(ru.getId()).getFlowers().size() - 1;
        int countForCountryFromDB = countryRepository.findByIdFromDB(ru.getId()).getFlowers().size() - 1;
        assertEquals(countForCountry, countForCountryFromDB);

        //flower deleting
        flowerRepository.delete(flower);

        //getting updated store and country
        store = storeRepository.findById(store.getId());
        ru = countryRepository.findById(ru.getId());

        //checking that flower removed from DB and repository sides
        assertEquals(flowerRepository.findAll().size(), flowerCount);
        assertEquals(flowerRepository.findAllFromDB().size(), flowerCountFromDB);

        //checking that store doesn't contain removed flower from DB and repository sides
        assertEquals(storeRepository.findById(store.getId()).getFlowers().size(), countForStore);
        assertEquals(storeRepository.findByIdFromDB(store.getId()).getFlowers().size(), countForStoreFromDB);

        //checking that country doesn't contain removed flower from DB and repository sides
        assertEquals(countryRepository.findById(ru.getId()).getFlowers().size(), countForCountry);
        assertEquals(countryRepository.findByIdFromDB(ru.getId()).getFlowers().size(), countForCountryFromDB);
    }

    @Test
    void deleteCountry_FlowerCheck() {
        //saving a new Store
        String storeName = "SPAR";
        Store store = new Store();
        store.setName(storeName);
        storeRepository.add(store);

        //saving a new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flower.setStore(store);
        flowerRepository.add(flower);

        //saving a new Country
        String countryName = "Russia";
        Country ru = new Country();
        ru.setName(countryName);
        countryRepository.add(ru);

        //saving their association
        ru.addFlower(flower);
        flowerRepository.update(flower);
        countryRepository.update(ru);

        //saving current countries count from DB and repository sides
        int countryCount = countryRepository.findAll().size() - 1;
        int countryCountFromDB = countryRepository.findAllFromDB().size() - 1;
        assertEquals(countryCount, countryCountFromDB);

        //saving current countries for flower count from DB and repository sides
        int countForFlower = flowerRepository.findById(flower.getId()).getCountries().size() - 1;
        int countForFlowerFromDB = flowerRepository.findByIdFromDB(flower.getId()).getCountries().size() - 1;
        assertEquals(countForFlower, countForFlowerFromDB);

        //country deleting
        countryRepository.delete(ru);

        //getting updated flower
        flower = flowerRepository.findById(flower.getId());

        //checking that country removed from DB and repository sides
        assertEquals(countryRepository.findAll().size(), countryCount);
        assertEquals(countryRepository.findAllFromDB().size(), countryCountFromDB);

        //checking that flower doesn't contain removed country from DB and repository sides
        assertEquals(flowerRepository.findById(flower.getId()).getCountries().size(), countForFlower);
        assertEquals(flowerRepository.findByIdFromDB(flower.getId()).getCountries().size(), countForFlowerFromDB);
    }

}