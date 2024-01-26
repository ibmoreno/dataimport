package com.dataimport.api.application.gateway;

import com.dataimport.api.domain.Customers;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomersGateway {
    Page<Customers> findAll(Pageable pageable);

    Optional<Customers> getOne(Integer id);

    Customers save(Customers customers);

    void delete(Customers customers);

    Page<Customers> search(String search, Pageable pageable);
}
