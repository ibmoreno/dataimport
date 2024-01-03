package com.dataimport.api;

import java.io.InputStream;
import java.util.List;

public interface ImportDataToBalanceSheetService {
    void execute(Integer customerId, Integer year, List<Integer> months, InputStream file);
}
