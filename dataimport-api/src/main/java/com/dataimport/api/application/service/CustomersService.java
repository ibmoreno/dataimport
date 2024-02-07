package com.dataimport.api.application.service;

import com.dataimport.api.application.gateway.CustomersGateway;
import com.dataimport.api.application.usecase.balance_sheet.importdata.ImportDataToBalanceSheet;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.exception.NotFoundException;
import com.dataimport.api.infra.controller.dto.customers.NewCustomersRequest;
import com.dataimport.api.infra.controller.dto.customers.UpdateCustomersRequest;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CustomersService {
    Page<Customers> getAll(Pageable pageable);

    Customers getOne(Integer id);

    Customers create(NewCustomersRequest newCustomersRequest);

    Customers update(Integer id, UpdateCustomersRequest updateCustomersRequest);

    void delete(Integer id);

    Page<Customers> search(String search, Pageable pageable);

    void importMovementAccount(Integer customerId, Integer year, List<Integer> months, InputStream file);

}

@Service
@RequiredArgsConstructor
class CustomersServiceImpl implements CustomersService {

    private final CustomersGateway customersGateway;
    private final ImportDataToBalanceSheet importDataToBalanceSheet;

    @Override
    public Page<Customers> getAll(Pageable pageable) {
        return customersGateway.findAll(pageable);
    }

    @Override
    public Customers getOne(Integer id) {
        return customersGateway.getOne(id)
                .orElseThrow(() -> new NotFoundException("Customers not found"));
    }

    @Override
    public Customers create(NewCustomersRequest newCustomersRequest) {
        Customers customers = newCustomersRequest.toDomain();
        return customersGateway.save(customers);
    }

    @Override
    public Customers update(Integer id, UpdateCustomersRequest updateCustomersRequest) {
        Customers customers = customersGateway.getOne(id)
                .map(c -> updateCustomersRequest.toDomain(c.getId()))
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return customersGateway.save(customers);
    }

    @Override
    public void delete(Integer id) {
        Customers customers = customersGateway.getOne(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        customersGateway.delete(customers);
    }

    @Override
    public Page<Customers> search(String search, Pageable pageable) {
        return customersGateway.search(search, pageable);
    }

    @Override
    @Async("asyncExecutor")
    @Transactional
    public void importMovementAccount(Integer customerId, Integer year, List<Integer> months, InputStream file) {
        Customers customers = this.getOne(customerId);
        importDataToBalanceSheet.execute(customers, year, months, file);
    }

}
