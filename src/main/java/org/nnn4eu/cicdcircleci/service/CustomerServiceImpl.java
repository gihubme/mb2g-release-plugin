package org.nnn4eu.cicdcircleci.service;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.cicdcircleci.domain.Customer;
import org.nnn4eu.cicdcircleci.repository.CustomerRepository;
import org.nnn4eu.cicdcircleci.web.mapper.CustomerMapper;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper MAPPER;// = Mappers.getMapper(CustomerMapper.class);


    @Transactional(readOnly = true) //will allow to get all collections used by MAPPER
    @Override
    public CustomerDto getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(MAPPER::customerToDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto dto) {
        return MAPPER.customerToDto(saveCustomerToDb(dto));
    }

    @Transactional(readOnly = false)
    //only after transaction is closed we get @CreationTimestamp and @UpdateTimestamp set
    public Customer saveCustomerToDb(CustomerDto dto) {
        return customerRepository.save(MAPPER.dtoToCustomer(dto));
    }

    @Transactional(readOnly = false)
    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        if (!customerRepository.existsById(id)) throw new EntityNotFoundException();
        dto.setId(id);
        return MAPPER.customerToDto(saveCustomerToDb(dto));
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) throw new EntityNotFoundException();
        customerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerDto> getAllCustomers(int page, int size, String dir, String prop) {
        Pageable paging;
        prop = (prop == null || prop.isBlank()) ? "secondName" : prop;
        if ("desc".equals(dir)) {
            paging = PagingUtil.createPageRequest(page, size, Sort.Direction.DESC, prop);
        } else {
            paging = PagingUtil.createPageRequest(page, size, Sort.Direction.ASC, prop);
        }
        return customerRepository.findAll(paging).map(MAPPER::customerToDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDto> getCustomersByPropStr(String val, String propStr) {
        List<Customer> customers = new ArrayList<>();
        switch (propStr) {
            case "firstName":
                customers.addAll(customerRepository.findByFirstNameContaining(val));
                break;
            case "secondName":
                customers.addAll(customerRepository.findBySecondNameContaining(val));
                break;
            default:
                break;
        }
        return customers.stream().map(MAPPER::customerToDto).collect(Collectors.toList());
    }


}
