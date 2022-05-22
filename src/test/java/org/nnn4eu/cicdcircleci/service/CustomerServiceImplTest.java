package org.nnn4eu.cicdcircleci.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nnn4eu.cicdcircleci.DataGenerator;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.repository.CustomerRepository;
import org.nnn4eu.cicdcircleci.web.mapper.*;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @BeforeAll
    public void setup() {
        DateMapper dateMapper = new DateMapper();

        MAddressMapper mAddressMapper = Mappers.getMapper(MAddressMapper.class);
        ReflectionTestUtils.setField(mAddressMapper, "dateMapper", dateMapper);

        MEmailMapper mEmailMapper = Mappers.getMapper(MEmailMapper.class);
        ReflectionTestUtils.setField(mEmailMapper, "dateMapper", dateMapper);

        MPhoneMapper mPhoneMapper = new MPhoneMapper(dateMapper);

        ReflectionTestUtils.setField(MAPPER, "mAddressMapper", mAddressMapper);
        ReflectionTestUtils.setField(MAPPER, "mEmailMapper", mEmailMapper);
        ReflectionTestUtils.setField(MAPPER, "mPhoneMapper", mPhoneMapper);
        ReflectionTestUtils.setField(MAPPER, "dateMapper", dateMapper);

    }

    private final CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    @BeforeEach
        // at this phase repository is not null and can be passed to service
    void init_mocks() {
        // MockitoAnnotations.initMocks(this);
        service = new CustomerServiceImpl(repository, MAPPER);
    }

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    void getCustomerById() {
        Customer customer = DataGenerator.createCustomer("Brigitte");
        customer.setId(21L);
//        MAPPER.INSTANCE.customerToDto(customer);
        CustomerDto dtoMapped = MAPPER.customerToDto(customer);
        Mockito.when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(customer));

        CustomerDto dto = service.getCustomerById(21L);

        Assertions.assertThat(dto).isEqualTo(dtoMapped);
        Assertions.assertThat(dto.getId()).isEqualTo(21L);
    }

    @Test
    void saveNewCustomer() {
        Customer customer = DataGenerator.createCustomer("Brigitte");
        customer.setId(21L);
        CustomerDto dtoMapped = MAPPER.customerToDto(customer);
        dtoMapped.setId(null);

        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerDto dto = service.saveNewCustomer(dtoMapped);

        Assertions.assertThat(dto).isNotEqualTo(dtoMapped);
        Assertions.assertThat(dto.getId()).isEqualTo(21L);
        Assertions.assertThat(dto.getFirstName()).isEqualTo(dtoMapped.getFirstName());
    }

    @Test
    void updateCustomer() {
        Customer customer = DataGenerator.createCustomer("Brigitte");
        customer.setId(21L);
        CustomerDto dtoMapped = MAPPER.customerToDto(customer);

        Customer customerUpdated = MAPPER.dtoToCustomer(dtoMapped);
        customerUpdated.setFirstName("NotBrigitte");
        customerUpdated.setPhones(null);
        CustomerDto dtoMappedUpdated = MAPPER.customerToDto(customerUpdated);
        dtoMappedUpdated.setId(null);

        Mockito.when(repository.existsById(21L)).thenReturn(true);
        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(customerUpdated);

        CustomerDto dtoMappedUpdated2 = service.updateCustomer(customer.getId(), dtoMappedUpdated);

        Assertions.assertThat(dtoMappedUpdated2).isEqualTo(dtoMappedUpdated);
        Assertions.assertThat(dtoMappedUpdated2.getId()).isEqualTo(21L);
        Assertions.assertThat(dtoMappedUpdated2.getFirstName()).isNotEqualTo(dtoMapped.getFirstName());
        Assertions.assertThat(dtoMappedUpdated2.getPhones()).isNull();

    }


}
