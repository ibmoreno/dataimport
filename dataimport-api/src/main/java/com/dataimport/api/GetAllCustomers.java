package com.dataimport.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllCustomers {

    Page<CustomersResponse> execute(Pageable pageable);

}