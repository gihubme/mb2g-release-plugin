package org.nnn4eu.cicdcircleci.service;

import org.nnn4eu.cicdcircleci.web.model.CustomerDto;

public interface CustomerService {
    CustomerDto getCustomerById(Long customerId);

    CustomerDto saveNewCustomer(CustomerDto dto);

    CustomerDto updateCustomer(Long id, CustomerDto dto);

    void deleteCustomer(Long id);

}
