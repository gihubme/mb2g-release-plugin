package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class, MPhoneMapper.class, MEmailMapper.class, MAddressMapper.class})
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}
