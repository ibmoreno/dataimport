package com.dataimport.api;

import java.io.InputStream;
import java.util.List;

public interface ReadFile {

    List<DataOutput> execute(InputStream file, MatchData matchData);

    ReadModelVersion getReadModelVersion();

}
