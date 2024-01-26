package com.dataimport.api.application.gateway;

import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.Status;
import java.util.List;

public interface AccountingAccountsGateway {
    List<AccountingAccounts> findAllByStatus(Status status);
}
