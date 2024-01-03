package com.dataimport.api;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReadFileV02Impl implements ReadFile {
    @Override
    public List<DataOutput> execute(InputStream file, MatchData matchData) {
        return Collections.emptyList();
    }

    @Override
    public ReadModelVersion getReadModelVersion() {
        return ReadModelVersion.V02;
    }
}
