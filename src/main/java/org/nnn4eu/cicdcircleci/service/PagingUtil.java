package org.nnn4eu.cicdcircleci.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public class PagingUtil {
    /*
    page: zero-based page index, must NOT be negative.
    size: number of items in a page to be returned, must be greater than 0.
    */
    public static Pageable createPageRequest(Integer pageBaseIndex, Integer pageSize,
                                             Map<Sort.Direction, List<String>> sorts) {
        List<String> ascProps = sorts.get(Sort.Direction.ASC);  // TODO check desc and asc don't share props
        List<String> descProps = sorts.get(Sort.Direction.DESC);
        Sort toSort = Sort.by(Sort.Direction.ASC, ascProps.toArray(new String[0]))
                .and(Sort.by(Sort.Direction.DESC, descProps.toArray(new String[0])));
        return PageRequest.of(pageBaseIndex, pageSize, toSort);
    }

    public static Pageable createPageRequest(Integer pageBaseIndex, Integer pageSize, Sort.Direction direction,
                                             String propertyToSort) {
        return PageRequest.of(pageBaseIndex, pageSize, direction, propertyToSort);//Sort.Direction.ASC
    }

    public static Pageable createPageRequest(Integer pageBaseIndex, Integer pageSize) {
        return PageRequest.of(pageBaseIndex, pageSize);
    }

// Order order1 = new Order(Sort.Direction.DESC, "published");
// Page<Foo> page  = new PageImpl<Foo>(fooList.subList(start, end), pageable, fooList.size());
    
}
