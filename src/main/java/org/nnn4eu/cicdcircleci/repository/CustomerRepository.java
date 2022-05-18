package org.nnn4eu.cicdcircleci.repository;

import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
