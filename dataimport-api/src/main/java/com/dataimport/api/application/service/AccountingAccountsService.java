package com.dataimport.api.application.service;

import com.dataimport.api.application.gateway.AccountingAccountsGateway;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.exception.NotFoundException;
import com.dataimport.api.infra.controller.dto.accounting_accounts.NewAccountingAccountsRequest;
import com.dataimport.api.infra.controller.dto.accounting_accounts.UpdateAccountingAccountsRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface AccountingAccountsService {
    Page<AccountingAccounts> getAll(Pageable pageable);

    AccountingAccounts getOne(Integer id);

    AccountingAccounts create(NewAccountingAccountsRequest newAccountingAccountsRequest);

    AccountingAccounts update(Integer id, UpdateAccountingAccountsRequest updateAccountingAccountsRequest);

    void delete(Integer id);

    Page<AccountingAccounts> search(String search, Pageable pageable);

}

@Service
@RequiredArgsConstructor
class AccountingAccountsServiceImpl implements AccountingAccountsService {

    private final AccountingAccountsGateway accountingAccountsGateway;

    @Override
    public Page<AccountingAccounts> getAll(Pageable pageable) {
        return accountingAccountsGateway.findAll(pageable);
    }

    @Override
    public AccountingAccounts getOne(Integer id) {
        return accountingAccountsGateway.getOne(id)
                .orElseThrow(() -> new NotFoundException("Accounting Accounts not found"));
    }

    @Override
    public AccountingAccounts create(NewAccountingAccountsRequest newAccountingAccountsRequest) {
        AccountingAccounts accountingAccounts = newAccountingAccountsRequest.toDomain();
        return accountingAccountsGateway.save(accountingAccounts);
    }

    @Override
    public AccountingAccounts update(Integer id, UpdateAccountingAccountsRequest updateAccountingAccountsRequest) {
        AccountingAccounts accountingAccounts = accountingAccountsGateway.getOne(id)
                .map(a -> updateAccountingAccountsRequest.toDomain(a.getId()))
                .orElseThrow(() -> new NotFoundException("Accounting Accounts not found"));
        return accountingAccountsGateway.save(accountingAccounts);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        AccountingAccounts accountingAccounts = accountingAccountsGateway.getOne(id)
                .orElseThrow(() -> new NotFoundException("Accounting Accounts not found"));

        List<AccountingAccounts> accountingAccountsAggregateIds =
                accountingAccountsGateway.findAllByAggregateAccountId(accountingAccounts.getId());

        if (!accountingAccountsAggregateIds.isEmpty()) {
            accountingAccountsAggregateIds.forEach(AccountingAccounts::removeAggregateAccount);
            accountingAccountsGateway.saveAll(accountingAccountsAggregateIds);
        }

        accountingAccountsGateway.delete(accountingAccounts);
    }

    @Override
    public Page<AccountingAccounts> search(String search, Pageable pageable) {
        return accountingAccountsGateway.search(search, pageable);
    }
}
