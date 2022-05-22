package org.nnn4eu.cicdcircleci.service;

import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerDto getCustomerById(Long customerId);

    CustomerDto saveNewCustomer(CustomerDto dto);

    CustomerDto updateCustomer(Long id, CustomerDto dto);

    void deleteCustomer(Long id);

    Page<CustomerDto> getAllCustomers(int page, int size, String dir, String prop);

    List<CustomerDto> getCustomersByPropStr(String val, String strprop);
}
