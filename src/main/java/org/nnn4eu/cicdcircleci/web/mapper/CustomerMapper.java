package org.nnn4eu.cicdcircleci.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;

@Mapper(componentModel = "spring",
//        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {DateMapper.class, MPhoneMapper.class, MEmailMapper.class, MAddressMapper.class})
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer dtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToDto(Customer customer);
}
