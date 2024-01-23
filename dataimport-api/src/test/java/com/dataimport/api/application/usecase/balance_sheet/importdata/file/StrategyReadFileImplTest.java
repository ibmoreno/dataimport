package com.dataimport.api.application.usecase.balance_sheet.importdata.file;

import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.exception.NotFoundException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StrategyReadFileImplTest {

    private StrategyReadFileImpl strategyReadFileImpl;

    @BeforeEach
    void setUp() {
        ReadFile readFileV01 = mock(ReadFile.class);
        ReadFile readFileV02 = mock(ReadFile.class);
        this.strategyReadFileImpl = new StrategyReadFileImpl(
                List.of(readFileV01, readFileV02)
        );
        when(readFileV01.getReadModelVersion()).thenReturn(ReadModelVersion.V01);
        when(readFileV02.getReadModelVersion()).thenReturn(ReadModelVersion.V02);
    }

    @Test
    void shouldReturnReadFileV01() {
        ReadFile readFile = strategyReadFileImpl.getReadFile(ReadModelVersion.V01);
        Assertions.assertEquals(ReadModelVersion.V01, readFile.getReadModelVersion());
    }

    @Test
    void shouldReturnReadFileV02() {
        ReadFile readFile = strategyReadFileImpl.getReadFile(ReadModelVersion.V02);
        Assertions.assertEquals(ReadModelVersion.V02, readFile.getReadModelVersion());
    }

    @Test
    void shouldReturnNotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () -> strategyReadFileImpl.getReadFile(null));
    }

}