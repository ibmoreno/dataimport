package com.dataimport.api.application.usecase.balance_sheet.importdata.file.version;

import com.dataimport.api.application.usecase.balance_sheet.importdata.file.ReadFile;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFileV02Test {

    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.readFile = new ReadFileV02();
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

        try (InputStream inputStream = ReadFileV02Test.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            Set<AccountingAccountsEntity> accounts = new HashSet<>();
            accounts.add(AccountingAccountsEntity.builder().id(1).description("Ativo Circulante").build());
            accounts.add(AccountingAccountsEntity.builder().id(2).description("Caixa e Equivalentes de Caixa").build());

            MatchData filter = MatchData.builder()
                    .year(2023)
                    .months(List.of(1))
                    .accounts(accounts)
                    .build();

            List<DataOutput> result = readFile.execute(inputStream, filter);
            Assertions.assertTrue(result.isEmpty());
            Assertions.assertEquals(ReadModelVersion.V02, readFile.getReadModelVersion());

        }

    }
}