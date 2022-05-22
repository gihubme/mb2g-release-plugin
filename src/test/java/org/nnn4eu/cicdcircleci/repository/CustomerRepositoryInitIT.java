package org.nnn4eu.cicdcircleci.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nnn4eu.cicdcircleci.DataGenerator;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = {"classpath:application-test.properties", "classpath:application-postgres.properties"})
@DataJpaTest(properties = {
        "spring.test.database.replace=NONE"
//        https://www.testcontainers.org/modules/databases/jdbc/
        , "spring.datasource.url=jdbc:tc:postgresql:12:///springboot"
})
public class CustomerRepositoryInitIT {
    @Autowired
    private CustomerRepository customerRepository;


    @BeforeEach
    void initData() {
        customerRepository.save(DataGenerator.createCustomer("Josefina"));
        customerRepository.save(DataGenerator.createCustomer("Ifina"));
        customerRepository.save(DataGenerator.createCustomer("Kelly"));
    }

    @Test
    void shouldReturnCustomersThatNameContainString() {
        List<Customer> customers = customerRepository.findBySecondNameContaining("son-");
        assertEquals(customerRepository.count(), customers.size());

        List<Customer> customers2 = customerRepository.findByFirstNameContaining("fina");
        assertEquals(2, customers2.size());
    }

    @Test
    void shouldReturnAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        assertEquals(3, customers.size());
    }

}
