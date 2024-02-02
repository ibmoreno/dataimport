package com.dataimport.api.application.usecase.balance_sheet.importdata.file.version;

import com.dataimport.api.application.usecase.balance_sheet.importdata.file.ReadFile;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.exception.ReadFileException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
class ReadFileV01 implements ReadFile {

    private static final String SHEET_NAME = "Demonstrativos";

    public List<DataOutput> execute(InputStream file, MatchData matchData) {

        log.info("read file model v01");

        Map<String, Integer> accountsMatch = matchData.getAccounts()
                .stream()
                .collect(Collectors.toMap(account -> account.getDescription().toLowerCase()
                        , AccountingAccounts::getId));

        try {

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            List<DataOutput> dataOutputs = new ArrayList<>();

            for (Row row : sheet) {
                for (int month : matchData.getMonths()) {

                    Cell accountDescription = row.getCell(0);
                    Cell accountMonth = row.getCell(month + 1);

                    if (accountDescription == null || accountMonth == null ||
                            !StringUtils.hasText(accountDescription.getStringCellValue())) {
                        continue;
                    }

                    Integer accountId = accountsMatch.get(accountDescription.getStringCellValue().toLowerCase());
                    if (accountId != null) {
                        dataOutputs.add(DataOutput.builder()
                                .accountingAccountsId(accountId)
                                .monthYear(YearMonth.of(matchData.getYear(), month).atDay(1))
                                .value(BigDecimal.valueOf(accountMonth.getNumericCellValue())
                                ).build());
                    }

                }
            }

            workbook.close();
            return dataOutputs;

        } catch (Exception e) {
            throw new ReadFileException(e.getMessage());
        }

    }

    @Override
    public ReadModelVersion getReadModelVersion() {
        return ReadModelVersion.V01;
    }

}
