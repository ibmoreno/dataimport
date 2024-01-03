package com.dataimport.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadFileV02ImplTest {

    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.readFile = new ReadFileV02Impl();
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

        try (InputStream inputStream = ReadFileV02ImplTest.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            Set<AccountingAccounts> accounts = new HashSet<>();
            accounts.add(AccountingAccounts.builder().id(1).description("Ativo Circulante").build());
            accounts.add(AccountingAccounts.builder().id(2).description("Caixa e Equivalentes de Caixa").build());

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