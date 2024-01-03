package com.dataimport.api;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

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
        List<AccountingAccounts> accountingAccounts = accountingAccountsRepository.findAllByStatus(Status.A);
        assertNotNull(accountingAccounts);
        assertFalse(accountingAccounts.isEmpty());
    }
}