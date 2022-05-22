package org.nnn4eu.cicdcircleci.repository;

import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { //PagingAndSortingRepository<Customer, Long>

    List<Customer> findByFirstNameContaining(@Param("val") String val);

    List<Customer> findBySecondNameContaining(@Param("val") String val);

//    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
//    Foo retrieveByName(@Param("name") String name);
}
