package org.nnn4eu.cicdcircleci.web.controller;

import org.nnn4eu.cicdcircleci.service.CustomerService;
import org.nnn4eu.cicdcircleci.web.model.CustomerDto;
import org.nnn4eu.cicdcircleci.web.model.CustomerDtoPageList;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<CustomerDtoPageList> getCustomers(@RequestParam(defaultValue = "ab", name = "val") String val,
                                                            @RequestParam(defaultValue = "secondName", name = "propStr")
                                                                    String propStr) {

        Boolean ok = Arrays.stream(CustomerDto.class.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(propStr) &&
                        "java.lang.String".equals(f.getGenericType().getTypeName()));
        if (!ok) throw new EntityNotFoundException();

        List<CustomerDto> result = customerService.getCustomersByPropStr(val, propStr);
        if (result.size() == 0) throw new EntityNotFoundException();

        CustomerDtoPageList found = new CustomerDtoPageList(result);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping //(params = {"page", "size", "dir", "prop"})
    public ResponseEntity<CustomerDtoPageList> getCustomers(
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "size") int size,
            @RequestParam(required = false, defaultValue = "asc", name = "dir") String dir,
            @RequestParam(required = false, defaultValue = "secondName", name = "prop") String prop) {
        Page<CustomerDto> resultPage = customerService.getAllCustomers(page, size, dir, prop);
        if (page > resultPage.getTotalPages()) {
            throw new EntityNotFoundException();
        }
        CustomerDtoPageList found = new CustomerDtoPageList(resultPage.getContent());
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Long customerId) {
        CustomerDto found = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody CustomerDto dto) {
        CustomerDto savedDto = customerService.saveNewCustomer(dto);
        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v1/customer/" + savedDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity handlePut(@PathVariable("customerId") Long id, @Valid @RequestBody CustomerDto dto) {
        CustomerDto savedDto = customerService.updateCustomer(id, dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long id) {
        customerService.deleteCustomer(id);
    }

}
