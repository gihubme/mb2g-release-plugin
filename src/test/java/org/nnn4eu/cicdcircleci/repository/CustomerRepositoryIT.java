package org.nnn4eu.cicdcircleci.repository;

import org.junit.jupiter.api.Test;
import org.nnn4eu.cicdcircleci.DataGenerator;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.driver-class-name=",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "spring.sql.init.platform=postgres"})
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryIT {
    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldReturnCustomersThatNameContainString() {
        customerRepository.save(DataGenerator.createCustomer("Josefina"));
        customerRepository.save(DataGenerator.createCustomer("Ifina"));
        customerRepository.save(DataGenerator.createCustomer("Kelly"));

        List<Customer> customers = customerRepository.findBySecondNameContaining("son-");
        assertEquals(customerRepository.count(), customers.size());

        List<Customer> customers2 = customerRepository.findByFirstNameContaining("fina");
        assertEquals(2, customers2.size());
    }
}
