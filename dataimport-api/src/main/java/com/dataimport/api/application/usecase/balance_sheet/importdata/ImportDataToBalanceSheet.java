package com.dataimport.api.application.usecase.balance_sheet.importdata;

import com.dataimport.api.application.gateway.AccountingAccountsGateway;
import com.dataimport.api.application.gateway.BalanceSheetGateway;
import com.dataimport.api.application.usecase.balance_sheet.importdata.file.StrategyReadFile;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.BalanceSheet;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.Status;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ImportDataToBalanceSheet {
    void execute(Customers customers, Integer year, List<Integer> months, InputStream file);
}

@Service
@RequiredArgsConstructor
@Slf4j
class ImportDataToBalanceSheetImpl implements ImportDataToBalanceSheet {

    private final AccountingAccountsGateway accountingAccountsGateway;
    private final BalanceSheetGateway balanceSheetGateway;
    private final StrategyReadFile strategyReadFile;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(10);


    @Transactional
    public void execute(Customers customers, Integer year, List<Integer> months, InputStream file) {

        CompletableFuture.runAsync(() -> {

                    log.info("Importing movement account for customer {} and months {} and year {}",
                            customers.getId(), months, year);

                    List<AccountingAccounts> accounts = accountingAccountsGateway.findAllByStatus(Status.A);
                    if (accounts.isEmpty()) {
                        return;
                    }

                    MatchData filter = MatchData.builder()
                            .year(year)
                            .months(months)
                            .accounts(new HashSet<>(accounts))
                            .build();

                    List<DataOutput> accountImports = strategyReadFile.getReadFile(customers.getReadModelVersion())
                            .execute(file, filter);

                    List<BalanceSheet> balanceSheetEntities = accountImports.stream().map(account ->
                            BalanceSheet.builder()
                                    .customersId(customers.getId())
                                    .accountingAccountsId(account.getAccountingAccountsId())
                                    .monthYear(account.getMonthYear())
                                    .costValue(account.getValue())
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build()
                    ).toList();

                    balanceSheetGateway.saveAll(balanceSheetEntities);

                }, forkJoinPool).exceptionally(exception -> {
                    log.error("Error import movement account for customer {} and months {} and year {}",
                            customers.getId(), months, year, exception);
                    return null;
                })
                .thenAcceptAsync(result ->
                        log.info("Complete import movement account for customer {} and months {} and year {}",
                                customers.getId(), months, year)
                );

    }

}