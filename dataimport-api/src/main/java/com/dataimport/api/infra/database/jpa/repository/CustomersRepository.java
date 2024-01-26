package com.dataimport.api.infra.database.jpa.repository;

import com.dataimport.api.infra.database.jpa.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<CustomersEntity, Integer>,
        JpaSpecificationExecutor<CustomersEntity> {
}
