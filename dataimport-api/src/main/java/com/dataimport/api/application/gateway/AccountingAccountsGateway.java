package com.dataimport.api.application.gateway;

import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountingAccountsGateway {
    List<AccountingAccounts> findAllByStatus(Status status);

    List<AccountingAccounts> findAllByAggregateAccountId(Integer accountingAccountsId);

    Page<AccountingAccounts> findAll(Pageable pageable);

    Optional<AccountingAccounts> getOne(Integer id);

    AccountingAccounts save(AccountingAccounts accountingAccounts);

    List<AccountingAccounts> saveAll(List<AccountingAccounts> accountingAccounts);

    void delete(AccountingAccounts customers);

    Page<AccountingAccounts> search(String search, Pageable pageable);


}
