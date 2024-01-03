package com.dataimport.api;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingAccountsRepository extends JpaRepository<AccountingAccounts, Integer> {
    List<AccountingAccounts> findAllByStatus(Status status);
}