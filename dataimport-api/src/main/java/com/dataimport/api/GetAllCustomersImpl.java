package com.dataimport.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllCustomersImpl implements GetAllCustomers {

    private final CustomersRepository customersRepository;
    @Override
    public Page<CustomersResponse> execute(Pageable pageable) {
        return customersRepository.findAll(pageable).map(CustomersResponse::fromEntity);
    }
}
