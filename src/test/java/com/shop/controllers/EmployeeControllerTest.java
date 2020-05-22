package com.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entities.Employee;
import com.shop.services.DaoService;
import com.shop.testConfigs.TestBaseConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestBaseConfig.class,
        EmployeeController.class
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {
    private MockMvc mvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    DaoService<Employee> employeeService;

    static Employee employee1 = new Employee();
    static Employee employee2 = new Employee();

    static {
        employee1.setName("Nobody");
        employee2.setName("Dr.Who");
    }

    @AfterAll
    void cleanUp() {
        em.close();
        entityManagerFactory.close();
    }


    @BeforeAll
    void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.getTransaction().commit();
    }

    @SneakyThrows
    @Test
    void getEmployees() {
        mvc.perform(MockMvcRequestBuilders
                .get("/employees")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void addEmployee() {
        Employee employee = new Employee();
        employee.setName("EMPLOYEE_TEST_NEW");
        String json = new ObjectMapper().writeValueAsString(employee);
        mvc.perform(MockMvcRequestBuilders
                .post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findEmployeeById() {
        Long id = 1L;
        mvc.perform(MockMvcRequestBuilders
                .get("/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @SneakyThrows
    @Test
    void updateEmployee() {
        Long id = 1L;
        String newName = "Nobody_NEW";
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = employeeService.findById(id);
        employee.setName(newName);
        String json = mapper.writeValueAsString(employee);
        mvc.perform(MockMvcRequestBuilders
                .post("/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .get("/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName));
    }

    @SneakyThrows
    @Test
    void deleteEmployee() {
        Long id = 2L;
        mvc.perform(MockMvcRequestBuilders
                .get("/employees/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}