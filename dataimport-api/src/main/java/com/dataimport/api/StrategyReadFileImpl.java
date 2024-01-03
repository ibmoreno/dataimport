package com.dataimport.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

interface StrategyReadFile {
    ReadFile getReadFile(ReadModelVersion version);
}

@Component
@RequiredArgsConstructor
public class StrategyReadFileImpl implements StrategyReadFile {

    private final List<ReadFile> readFiles;

    public ReadFile getReadFile(ReadModelVersion version) {
        return readFiles.stream()
                .filter(readFile -> readFile.getReadModelVersion().equals(version))
                .findFirst().orElseThrow(() -> new NotFoundException("Version not found"));
    }

}
