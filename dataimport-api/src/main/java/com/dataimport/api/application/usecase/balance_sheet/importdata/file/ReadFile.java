package com.dataimport.api.application.usecase.balance_sheet.importdata.file;

import com.dataimport.api.domain.DataOutput;
import com.dataimport.api.domain.MatchData;
import com.dataimport.api.domain.ReadModelVersion;
import java.io.InputStream;
import java.util.List;

public interface ReadFile {

    List<DataOutput> execute(InputStream file, MatchData matchData);

    ReadModelVersion getReadModelVersion();

}
