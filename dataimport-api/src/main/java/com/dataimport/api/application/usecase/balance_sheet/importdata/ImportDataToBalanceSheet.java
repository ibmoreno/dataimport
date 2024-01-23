package com.dataimport.api.application.usecase.balance_sheet.importdata;

import com.dataimport.api.application.usecase.balance_sheet.importdata.file.StrategyReadFile;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntityPk;
import com.dataimport.api.infra.database.jpa.repository.AccountingAccountsRepository;
import com.dataimport.api.infra.database.jpa.repository.BalanceSheetRepository;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ImportDataToBalanceSheet {
    void execute(Customers customers, Integer year, List<Integer> months, InputStream file);
}

@Service
@RequiredArgsConstructor
class ImportDataToBalanceSheetImpl implements ImportDataToBalanceSheet {

    private final AccountingAccountsRepository accountingAccountsRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final StrategyReadFile strategyReadFile;

    @Transactional
    public void execute(Customers customers, Integer year, List<Integer> months, InputStream file) {

        List<AccountingAccountsEntity> accounts = accountingAccountsRepository.findAllByStatus(Status.A);
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

        List<BalanceSheetEntity> balanceSheetEntities = accountImports.stream().map(account ->
                BalanceSheetEntity.builder()
                        .balanceSheetEntityPk(BalanceSheetEntityPk.builder()
                                .customersId(customers.getId())
                                .accountingAccountsId(account.getAccountingAccountsId())
                                .monthYear(account.getMonthYear())
                                .build())
                        .costValue(account.getValue())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).toList();

        balanceSheetRepository.saveAll(balanceSheetEntities);

    }

}