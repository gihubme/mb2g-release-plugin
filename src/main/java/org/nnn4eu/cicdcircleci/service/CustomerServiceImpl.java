package org.nnn4eu.cicdcircleci.service;

import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(Long customerId) {
        return null;
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto dto) {
        return null;
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }

}
