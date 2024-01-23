package com.dataimport.api.infra.gateway;

import com.dataimport.api.application.gateway.CustomersGateway;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.infra.database.jpa.entity.CustomersEntity;
import com.dataimport.api.infra.database.jpa.repository.CustomersRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersRepositoryGateway implements CustomersGateway {

    private final CustomersRepository customersRepository;

    @Override
    public Page<Customers> findAll(Pageable pageable) {
        return customersRepository.findAll(pageable).map(CustomersEntity::toDomain);
    }

    @Override
    public Optional<Customers> getOne(Integer id) {
        CustomersEntity customersEntity = customersRepository.getReferenceById(id);
        return Optional.of(customersEntity).map(CustomersEntity::toDomain);
    }

    @Override
    public Customers save(Customers customers) {
        CustomersEntity customersEntity = customers.toEntity();
        return customersRepository.save(customersEntity).toDomain();
    }

    @Override
    public void delete(Customers customers) {
        customersRepository.deleteById(customers.getId());
    }
}
