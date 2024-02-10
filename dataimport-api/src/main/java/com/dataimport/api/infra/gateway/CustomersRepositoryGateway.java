package com.dataimport.api.infra.gateway;

import com.dataimport.api.application.gateway.CustomersGateway;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.CustomersEntity;
import com.dataimport.api.infra.database.jpa.entity.CustomersEntity_;
import com.dataimport.api.infra.database.jpa.repository.CustomersRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomersRepositoryGateway implements CustomersGateway {

    private final CustomersRepository customersRepository;
    private static final Specification<CustomersEntity> statusInActiveAndInactive = (root, query, criteriaBuilder) ->
            criteriaBuilder.in(root.get(CustomersEntity_.STATUS)).value(List.of(Status.A, Status.I));


    @Override
    public Page<Customers> findAll(Pageable pageable) {
        return customersRepository.findAll(statusInActiveAndInactive, pageable).map(CustomersEntity::toDomain);
    }

    @Override
    public Optional<Customers> getOne(Integer id) {
        return customersRepository.findById(id).map(CustomersEntity::toDomain);
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

    @Override
    public Page<Customers> search(String search, Pageable pageable) {

        if (!StringUtils.hasText(search)) {
            return this.findAll(pageable);
        }

        StringBuilder pattern = new StringBuilder("%");
        pattern.append(search.toLowerCase());
        pattern.append("%");

        Specification<CustomersEntity> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(CustomersEntity_.NAME)), pattern.toString()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(CustomersEntity_.EMAIL)), pattern.toString()),
                        criteriaBuilder.like(root.get(CustomersEntity_.CNPJ), pattern.toString())
                );

        specification.and(statusInActiveAndInactive);
        return customersRepository.findAll(specification, pageable).map(CustomersEntity::toDomain);

    }
}
