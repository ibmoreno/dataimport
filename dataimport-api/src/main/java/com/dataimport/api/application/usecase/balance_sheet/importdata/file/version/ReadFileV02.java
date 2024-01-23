package com.dataimport.api.application.usecase.balance_sheet.importdata.file.version;

import com.dataimport.api.application.usecase.balance_sheet.importdata.file.ReadFile;
import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.ReadModelVersion;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class ReadFileV02 implements ReadFile {
    @Override
    public List<DataOutput> execute(InputStream file, MatchData matchData) {
        return Collections.emptyList();
    }

    @Override
    public ReadModelVersion getReadModelVersion() {
        return ReadModelVersion.V02;
    }
}
