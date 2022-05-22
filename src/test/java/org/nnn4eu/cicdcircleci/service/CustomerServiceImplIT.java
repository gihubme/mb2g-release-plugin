package org.nnn4eu.cicdcircleci.service;

import org.junit.jupiter.api.*;
import org.nnn4eu.cicdcircleci.DataDtoGenerator;
import org.nnn4eu.cicdcircleci.PostgreSQLContainerInitializer;
import org.nnn4eu.cicdcircleci.repository.CustomerRepository;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "dataloading.enabled=false",
        "spring.datasource.driver-class-name=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "spring.sql.init.platform=postgres"},
                    locations = {"classpath:application-test.properties"})

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class}
)
class CustomerServiceImplIT {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    private static List<CustomerDto> dtos = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        dtos.add(DataDtoGenerator.generateCustomerDto("Fina", "Brook", false));
        dtos.add(DataDtoGenerator.generateCustomerDto("Josefina", "Bree", false));
        dtos.add(DataDtoGenerator.generateCustomerDto("Josef", "Slipper", false));
    }

    @BeforeEach
    public void init() {
        customerRepository.deleteAll();
        customerRepository.flush();
        Assertions.assertEquals(customerRepository.count(), 0);
        dtos.set(0, customerService.saveNewCustomer(dtos.get(0)));
        dtos.set(1, customerService.saveNewCustomer(dtos.get(1)));
        dtos.set(2, customerService.saveNewCustomer(dtos.get(2)));
        customerRepository.flush();
        Assertions.assertEquals(customerRepository.count(), 3);
    }

    @Test
    @Order(value = 1)
    void saveNewCustomer() {
        Assertions.assertEquals(customerRepository.count(), 3);
        org.assertj.core.api.Assertions.assertThat(dtos.get(0).getId()).isNotEqualTo(null);
        org.assertj.core.api.Assertions.assertThat(dtos.get(0).getLastModifiedDate().toString()).isNotBlank();
        org.assertj.core.api.Assertions.assertThat(dtos.get(0).getAddresses()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(dtos.get(0).getAddresses().size()).isGreaterThan(0);
        org.assertj.core.api.Assertions.assertThat(dtos.get(0).getAddresses().get(0))
                .isNull();//as we have LazyLoaded collection
    }

    @Test
    @Order(value = 2)
    void updateCustomer() {
        Assertions.assertEquals(customerRepository.count(), 3);

        dtos.get(0).setFirstName("NewNameBilly");
        dtos.get(0).setSecondName("NewSurname");
        CustomerDto dtoUpdated = customerService.updateCustomer(dtos.get(0).getId(), dtos.get(0));
        org.assertj.core.api.Assertions.assertThat(dtoUpdated.getId()).isEqualTo(dtos.get(0).getId());
        org.assertj.core.api.Assertions.assertThat(dtoUpdated.getFirstName()).isEqualTo("NewNameBilly");
    }

    @Test
    @Order(3)
    void getCustomerById() {
        Assertions.assertEquals(customerRepository.count(), 3);
        CustomerDto dtoFound = customerService.getCustomerById(dtos.get(2).getId());
        Assertions.assertEquals(dtoFound, dtos.get(2));
    }

    @Test
    @Order(4)
    void deleteCustomer() {
        Assertions.assertEquals(customerRepository.count(), 3);
        Long idToDelete = dtos.get(0).getId();
        customerService.deleteCustomer(idToDelete);
        Assertions.assertEquals(customerRepository.count(), 2);
        Page<CustomerDto> page = customerService.getAllCustomers(0, 10, null, null);
        List<CustomerDto> customerDtos =
                page.get().filter(a -> a.getId().equals(idToDelete)).collect(Collectors.toList());
        Assertions.assertEquals(customerDtos.size(), 0);
    }

    @Test
    @Order(5)
    void getAllCustomers() {
        Assertions.assertEquals(customerRepository.count(), 3);
        Page<CustomerDto> page = customerService.getAllCustomers(0, 10, null, null);
        Assertions.assertEquals(page.getTotalElements(), 3);
    }

    @Test
    @Order(6)
    void getCustomersByPropStr() {
        Assertions.assertEquals(customerRepository.count(), 3);
        List<CustomerDto> founds = customerService.getCustomersByPropStr("sef", "firstName");
        Assertions.assertEquals(founds.size(), 2);
    }
}
