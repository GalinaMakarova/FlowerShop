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



-----
-----
-----
не хватает:
1. контроллеров для каждой сущности (Employee, Store, Flower)
2. странички для каждой сущности в отдельности
3. стартовая страница
4. Тесты для каждой crud операции по каждой сущности
5. Тесты для связок OneToOne/OneToMany/ManyToOne/ManyToMany
6. Скрипты на добавление даты

Вопросы: 
1. H2 была заменена на PostgreSQL, H2 удалялась для каждого запуска, подготовленные скрипты не прогонялись сразу после старта бд, но перед стартом приложения, непонятно почему
2. Для DaoCRUDImpl получается методы идентичные по add/update. Можно ли на этом уровне проставить логгер или нет, если нет, то почему
3. 