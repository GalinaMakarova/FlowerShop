package com.shop;

import repositories.EmployeeRepository;
import repositories.CountryRepository;
import repositories.FlowerRepository;
import repositories.StoreRepository;

public class Main {
    static FlowerRepository flowerRepository = new FlowerRepository();
    static CountryRepository countryRepository = new CountryRepository();
    static EmployeeRepository employeeRepository = new EmployeeRepository();
    static StoreRepository storeRepository = new StoreRepository();

    public static void main(String[] args) {

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

