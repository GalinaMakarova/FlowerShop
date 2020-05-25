# Flower Shop

The project has 4 entities:
1. Employee
2. Shop
3. Flower
4. Country

![alt text](INFORMATION_SCHEMA.png "Tables schema")

* Employee and Shop have association OneToOne, Shop can exist without Employee, but Employee without Shop should be removed automatically.
* Country and Flower have association ManyToMany, Country can exist without Flower, Flower without Country - not
* Shop and Flower have association OneToMany, one Shop for Many Flower objects. Flower without Shop cannot exist, Shop without Flower - can.

---

# To configure the app please change application.properties #
Set your database information for the following properties:
* spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
* spring.datasource.username=postgres
* spring.datasource.password=123
* packageName=com.shop

---

# Created for Markup Languages: #
1. Mapper for List<T> ot JSON part
2. Mapper for JSON to List<T> part
3. Tests for both mappers
4. Before JSON to List<T> part test inputFile.json should exist in the project folder
5. After List<T> ot JSON part test outputFile.json should be created automatically 



---
