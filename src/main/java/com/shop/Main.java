package com.shop;


import utils.DBConnection;
import utils.HibernateUtil;

import repositories.EmployeeRepository;
import repositories.CountryRepository;
import repositories.FlowerRepository;
import repositories.StoreRepository;

import entities.Employee;
import entities.Country;
import entities.Flower;
import entities.Store;

public class Main {
    static FlowerRepository flowerRepository = new FlowerRepository();
    static CountryRepository countryRepository = new CountryRepository();
    static EmployeeRepository employeeRepository = new EmployeeRepository();
    static StoreRepository storeRepository = new StoreRepository();

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        dbConnection.getConnection();

        Country ru = new Country();
        ru.setName("Russia");
        Country en = new Country();
        en.setName("England");
//
        Flower rose = new Flower();
        rose.setName("Rose");
//        Flower tulip = new Flower();
//        tulip.setName("Tulip");
//        Flower gerbera = new Flower();
//        gerbera.setName("Gerbera");
//
//        Employee sparEmployee = new Employee();
//        sparEmployee.setName("SPAR Employee");
        Employee auchanEmployee = new Employee();
        auchanEmployee.setName("Auchan Employee");

//        Store spar = new Store();
//        spar.setName("SPAR");
//        spar.setAddress("some street, 123");
//        spar.setEmployee(sparEmployee);
//        spar.addFlower(rose);
//        spar.addFlower(tulip);
//        spar.addFlower(gerbera);
//
        ru.addFlower(rose);
//        ru.addFlower(tulip);
        countryRepository.add(ru);
//        storeRepository.add(spar);
//
        en.addFlower(rose);
//        en.addFlower(tulip);
//        en.addFlower(gerbera);

        Store auchan = new Store();
        auchan.setName("Auchan");
        auchan.setAddress("other street, 000");
        auchan.setEmployee(auchanEmployee);
        auchan.addFlower(rose);
//        auchan.addFlower(tulip);
        countryRepository.add(en);
        storeRepository.add(auchan);

        System.out.println(countryRepository.findAll().size());

        //System.out.println(countryRepository.findByName("Russia").toString());

        //repositoriesConsole();

//        System.out.println("[UPDATE: FLOWER]");
//        flower.setName("gerbera");
//        storeRepository.update(store);
//        repositoriesConsole();
//
//        System.out.println("[UPDATE: COUNTRY]");
//        country.setName("England");
//        countryRepository.update(country);
//        storeRepository.update(store);
//        repositoriesConsole();
//
//        System.out.println("[UPDATE: EMPLOYEE]");
//        employee.setName("ZZZ");
//        storeRepository.update(store);
//        repositoriesConsole();
//
//        System.out.println("[UPDATE: STORE]");
//        store.setName("Auchan");
//        storeRepository.update(store);
//        repositoriesConsole();

        //System.out.println(flowerRepository.findAllCountries(rose).toString());


        HibernateUtil.shutdown();
    }

    static void repositoriesConsole() {
        System.out.println("----------Repositories console start----------");
        System.out.println("--Country Repository--");
        countryRepository.findAll().forEach(country -> System.out.println(country.toString()));
        System.out.println("--Flower Repository--");
        flowerRepository.findAll().forEach(flower -> System.out.println(flower.toString()));
        System.out.println("--Store Repository--");
        storeRepository.findAll().forEach(store -> System.out.println(store.toString()));
        System.out.println("--Employee Repository--");
        employeeRepository.findAll().forEach(employee -> System.out.println(employee.toString()));
        System.out.println("----------Repositories console end----------");
    }
}

