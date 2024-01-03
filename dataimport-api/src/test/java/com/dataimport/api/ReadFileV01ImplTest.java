package com.dataimport.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class ReadFileV01ImplTest {

    private ReadFile readFile;

    @BeforeEach
    void setUp() {
        this.readFile = new ReadFileV01Impl();
    }

    @Test
    void shouldImportFileFromExcel() throws IOException {

            try (InputStream inputStream = ReadFileV01ImplTest.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            Set<AccountingAccounts> accounts = new HashSet<>();
            accounts.add(AccountingAccounts.builder().id(1).description("Ativo Circulante").build());
            accounts.add(AccountingAccounts.builder().id(2).description("Caixa e Equivalentes de Caixa").build());

            MatchData filter = MatchData.builder()
                    .year(2023)
                    .months(List.of(1))
                    .accounts(accounts)
                    .build();

            List<DataOutput> result = readFile.execute(inputStream, filter);
            Assertions.assertEquals(2, result.size());
            Assertions.assertEquals(ReadModelVersion.V01, readFile.getReadModelVersion());

        }

    }

    @Test
    void shouldRunWithReadFileExceptionError() throws IOException {

        try (InputStream file = ReadFileV01ImplTest.class.getResourceAsStream("/file/movement_v01.xlsx")) {

            Set<AccountingAccounts> accounts = new HashSet<>();
            accounts.add(AccountingAccounts.builder().id(1).description("Ativo Circulante").build());
            accounts.add(AccountingAccounts.builder().id(2).description("Caixa e Equivalentes de Caixa").build());

            MatchData filter = MatchData.builder()
                    .months(List.of(1))
                    .accounts(accounts)
                    .build();

            Assertions.assertThrows(ReadFileException.class, () -> readFile.execute(file, filter));
            Assertions.assertEquals(ReadModelVersion.V01, readFile.getReadModelVersion());

        }

    }
}