package com.dataimport.api.infra.database.jpa.repository;

import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountingAccountsRepositoryTest {

    @Autowired
    private AccountingAccountsRepository accountingAccountsRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldListAllAccountingAccountsByStatus() {
        List<AccountingAccountsEntity> accountingAccounts = accountingAccountsRepository.findAllByStatus(Status.A);
        assertNotNull(accountingAccounts);
        assertFalse(accountingAccounts.isEmpty());
    }

}