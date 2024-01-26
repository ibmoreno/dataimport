package com.dataimport.api.infra.gateway;

import com.dataimport.api.application.gateway.AccountingAccountsGateway;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import com.dataimport.api.infra.database.jpa.repository.AccountingAccountsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountingAccountsRepositoryGateway implements AccountingAccountsGateway {

    private final AccountingAccountsRepository accountingAccountsRepository;

    @Override
    public List<AccountingAccounts> findAllByStatus(Status status) {
        return accountingAccountsRepository.findAllByStatus(status)
                .stream().map(AccountingAccountsEntity::toDomain).toList();
    }
}
