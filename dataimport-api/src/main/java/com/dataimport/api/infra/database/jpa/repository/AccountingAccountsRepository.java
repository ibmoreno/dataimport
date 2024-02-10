package com.dataimport.api.infra.database.jpa.repository;

import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingAccountsRepository extends JpaRepository<AccountingAccountsEntity, Integer>,
        JpaSpecificationExecutor<AccountingAccountsEntity> {
    List<AccountingAccountsEntity> findAllByStatus(Status status);
    List<AccountingAccountsEntity> findAllByAggregateAccount(AccountingAccountsEntity accountingAccountsEntity);
}