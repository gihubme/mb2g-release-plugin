package org.nnn4eu.cicdcircleci.repository;

import org.junit.jupiter.api.Test;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = {"classpath:application-test.properties", "classpath:application-postgres.properties"})
@DataJpaTest(properties = {
        "spring.test.database.replace=NONE"
//        https://www.testcontainers.org/modules/databases/jdbc/
        , "spring.datasource.url=jdbc:tc:postgresql:12:///springboot"
})
public class CustomerRepositoryShortIT {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Sql("/scripts/INIT_THREE_CUSTOMERS.sql")
    void shouldReturnOrdersThatContainMacBookPro() {
        List<Customer> customers = customerRepository.findAll();
        assertEquals(3, customers.size());
    }
}
