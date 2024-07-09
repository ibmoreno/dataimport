package com.dataimport.api.application.gateway;

import java.time.LocalDate;
import java.util.List;

public interface DeleteMovementAccountGateway {
    void execute(Integer customerId, List<LocalDate> monthYear);
}
