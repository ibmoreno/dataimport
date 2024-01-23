package com.dataimport.api.application.usecase.balance_sheet.importdata.file;

import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public interface StrategyReadFile {
    ReadFile getReadFile(ReadModelVersion version);
}

@Component
@RequiredArgsConstructor
class StrategyReadFileImpl implements StrategyReadFile {

    private final List<ReadFile> readFiles;

    public ReadFile getReadFile(ReadModelVersion version) {
        return readFiles.stream()
                .filter(readFile -> readFile.getReadModelVersion().equals(version))
                .findFirst().orElseThrow(() -> new NotFoundException("Version not found"));
    }

}
