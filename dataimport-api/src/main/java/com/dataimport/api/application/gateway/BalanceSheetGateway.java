package com.dataimport.api.application.gateway;

import com.dataimport.api.domain.BalanceSheet;
import java.util.List;

public interface BalanceSheetGateway {

    List<BalanceSheet> saveAll(List<BalanceSheet> balanceSheets);

}