package com.dataimport.api;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImportDataToBalanceSheetServiceImplTest {

    private ImportDataToBalanceSheetService importDataToBalanceSheetService;
    private AccountingAccountsRepository accountingAccountsRepository;
    private BalanceSheetRepository balanceSheetRepository;
    private CustomersRepository customersRepository;
    private StrategyReadFile strategyReadFile;
    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.accountingAccountsRepository = mock(AccountingAccountsRepository.class);
        this.balanceSheetRepository = mock(BalanceSheetRepository.class);
        this.customersRepository = mock(CustomersRepository.class);
        this.strategyReadFile = mock(StrategyReadFile.class);
        this.readFile = mock(ReadFile.class);
        this.importDataToBalanceSheetService = new ImportDataToBalanceSheetServiceImpl(
                accountingAccountsRepository,
                balanceSheetRepository,
                customersRepository,
                strategyReadFile);
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

        try (InputStream file = ImportDataToBalanceSheetService.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            when(customersRepository.findById(any()))
                    .thenReturn(Optional.of(Customers.builder()
                            .readModelVersion(ReadModelVersion.V01)
                            .build()));

            when(accountingAccountsRepository.findAllByStatus(any(Status.class)))
                    .thenReturn(List.of(AccountingAccounts.builder()
                            .id(1)
                            .description("Ativo Circulante")
                            .build()));

            when(strategyReadFile.getReadFile(any()))
                    .thenReturn(readFile);

            when(balanceSheetRepository.saveAll(anyList()))
                    .thenReturn(List.of(BalanceSheet.builder()
                            .balanceSheetPk(BalanceSheetPk.builder()
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

            importDataToBalanceSheetService.execute(1, 2023, List.of(1), file);

            verify(accountingAccountsRepository).findAllByStatus(any(Status.class));
            verify(balanceSheetRepository).saveAll(anyList());
            verify(customersRepository).findById(any());
            verify(strategyReadFile).getReadFile(any());
            verify(readFile).execute(any(InputStream.class), any(MatchData.class));

        }

    }

    @Test
    void shouldReturnNotFoundExceptionForCustomers() throws IOException {
        try (InputStream file = ImportDataToBalanceSheetService.class.getResourceAsStream("/file/movement_v01.xlsx")) {
            List<Integer> months = List.of(1, 2);
            Integer customerId = 1;
            Integer year = 2023;
            when(customersRepository.findById(any())).thenReturn(Optional.empty());
            Assertions.assertThrows(NotFoundException.class, () -> importDataToBalanceSheetService
                    .execute(customerId, year, months, file));
        }
    }

}