package org.nnn4eu.cicdcircleci.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomerDtoPageList extends PageImpl<CustomerDto> {
    public CustomerDtoPageList(List<CustomerDto> content, Pageable pageable,
                               long total) {
        super(content, pageable, total);
    }

    public CustomerDtoPageList(List<CustomerDto> content) {
        super(content);
    }
}
