package com.dataimport.api.application.usecase.balance_sheet.importdata;

import com.dataimport.api.application.usecase.balance_sheet.importdata.file.ReadFile;
import com.dataimport.api.application.usecase.balance_sheet.importdata.file.StrategyReadFile;
import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.Status;
import com.dataimport.api.exception.NotFoundException;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntityPk;
import com.dataimport.api.infra.database.jpa.repository.AccountingAccountsRepository;
import com.dataimport.api.infra.database.jpa.repository.BalanceSheetRepository;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImportDataToBalanceSheetImplTest {

    private ImportDataToBalanceSheet importDataToBalanceSheet;
    private AccountingAccountsRepository accountingAccountsRepository;
    private BalanceSheetRepository balanceSheetRepository;
    private StrategyReadFile strategyReadFile;
    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.accountingAccountsRepository = mock(AccountingAccountsRepository.class);
        this.balanceSheetRepository = mock(BalanceSheetRepository.class);
        this.strategyReadFile = mock(StrategyReadFile.class);
        this.readFile = mock(ReadFile.class);
        this.importDataToBalanceSheet = new ImportDataToBalanceSheetImpl(
                accountingAccountsRepository,
                balanceSheetRepository,
                strategyReadFile);
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

        try (InputStream file = ImportDataToBalanceSheet.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            when(accountingAccountsRepository.findAllByStatus(any(Status.class)))
                    .thenReturn(List.of(AccountingAccountsEntity.builder()
                            .id(1)
                            .description("Ativo Circulante")
                            .build()));

            when(strategyReadFile.getReadFile(any()))
                    .thenReturn(readFile);

            when(balanceSheetRepository.saveAll(anyList()))
                    .thenReturn(List.of(BalanceSheetEntity.builder()
                            .balanceSheetEntityPk(BalanceSheetEntityPk.builder()
                                    .customersId(1)
                                    .accountingAccountsId(1)
                                    .monthYear(YearMonth.of(2023, 1).atDay(1))
                                    .build())
                            .costValue(BigDecimal.valueOf(100))
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()));

            when(readFile.execute(any(InputStream.class), any(MatchData.class)))
                    .thenReturn(List.of(DataOutput.builder()
                            .accountingAccountsId(1)
                            .monthYear(YearMonth.of(2023, 1).atDay(1))
                            .value(BigDecimal.valueOf(100))
                            .build()));

            Customers customers = Customers.builder().id(1).readModelVersion(ReadModelVersion.V01).build();
            importDataToBalanceSheet.execute(customers, 2023, List.of(1), file);

            verify(accountingAccountsRepository).findAllByStatus(any(Status.class));
            verify(balanceSheetRepository).saveAll(anyList());
            verify(strategyReadFile).getReadFile(any());
            verify(readFile).execute(any(InputStream.class), any(MatchData.class));

        }

    }

    @Test
    void shouldReturnNotFoundExceptionForCustomers() throws IOException {
        try (InputStream file = ImportDataToBalanceSheet.class.getResourceAsStream("/file/movement_v01.xlsx")) {
            List<Integer> months = List.of(1, 2);
            Integer customerId = 1;
            Integer year = 2023;
            Customers customers = Customers.builder().id(1).readModelVersion(ReadModelVersion.V01).build();
            Assertions.assertThrows(NotFoundException.class, () -> importDataToBalanceSheet
                    .execute(customers, year, months, file));
        }
    }

}