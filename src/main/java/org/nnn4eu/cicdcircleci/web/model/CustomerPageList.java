package org.nnn4eu.cicdcircleci.web.model;

import org.nnn4eu.cicdcircleci.domain.Customer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomerPageList extends PageImpl<Customer> {
    public CustomerPageList(List<Customer> content, Pageable pageable,
                            long total) {
        super(content, pageable, total);
    }

    public CustomerPageList(List<Customer> content) {
        super(content);
    }
}
