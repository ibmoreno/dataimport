package com.dataimport.api;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImportDataToBalanceSheetServiceImpl implements ImportDataToBalanceSheetService {

    private final AccountingAccountsRepository accountingAccountsRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final CustomersRepository customersRepository;
    private final StrategyReadFile strategyReadFile;

    @Transactional
    public void execute(Integer customerId, Integer year, List<Integer> months, InputStream file) {

        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        List<AccountingAccounts> accounts = accountingAccountsRepository.findAllByStatus(Status.A);
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

        List<BalanceSheet> balanceSheets = accountImports.stream().map(account ->
                BalanceSheet.builder()
                        .balanceSheetPk(BalanceSheetPk.builder()
                                .customersId(customerId)
                                .accountingAccountsId(account.getAccountingAccountsId())
                                .monthYear(account.getMonthYear())
                                .build())
                        .costValue(account.getValue())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).toList();

        balanceSheetRepository.saveAll(balanceSheets);

    }

}
