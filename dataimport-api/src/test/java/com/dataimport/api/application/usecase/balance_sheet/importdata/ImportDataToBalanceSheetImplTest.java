package com.dataimport.api.application.usecase.balance_sheet.importdata;

import com.dataimport.api.application.gateway.AccountingAccountsGateway;
import com.dataimport.api.application.gateway.BalanceSheetGateway;
import com.dataimport.api.application.usecase.balance_sheet.importdata.file.ReadFile;
import com.dataimport.api.application.usecase.balance_sheet.importdata.file.StrategyReadFile;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.BalanceSheet;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.domain.Status;
import com.dataimport.api.exception.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
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
    private AccountingAccountsGateway accountingAccountsGateway;
    private BalanceSheetGateway balanceSheetGateway;
    private StrategyReadFile strategyReadFile;
    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.accountingAccountsGateway = mock(AccountingAccountsGateway.class);
        this.balanceSheetGateway = mock(BalanceSheetGateway.class);
        this.strategyReadFile = mock(StrategyReadFile.class);
        this.readFile = mock(ReadFile.class);
        this.importDataToBalanceSheet = new ImportDataToBalanceSheetImpl(
                accountingAccountsGateway,
                balanceSheetGateway,
                strategyReadFile);
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

        try (InputStream file = ImportDataToBalanceSheet.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            when(accountingAccountsGateway.findAllByStatus(any(Status.class)))
                    .thenReturn(List.of(AccountingAccounts.builder()
                            .id(1)
                            .description("Ativo Circulante")
                            .build()));

            when(strategyReadFile.getReadFile(any()))
                    .thenReturn(readFile);

            when(balanceSheetGateway.saveAll(anyList()))
                    .thenReturn(List.of(BalanceSheet.builder()
                            .customersId(1)
                            .accountingAccountsId(1)
                            .monthYear(YearMonth.of(2023, 1).atDay(1))
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

            verify(accountingAccountsGateway).findAllByStatus(any(Status.class));
            verify(balanceSheetGateway).saveAll(anyList());
            verify(strategyReadFile).getReadFile(any());
            verify(readFile).execute(any(InputStream.class), any(MatchData.class));

        }

    }

}