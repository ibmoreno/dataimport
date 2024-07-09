package com.dataimport.api.infra.gateway;

import com.dataimport.api.application.gateway.DeleteMovementAccountGateway;
import com.dataimport.api.infra.database.jpa.repository.BalanceSheetRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMovementRepositoryGateway implements DeleteMovementAccountGateway {
    private final BalanceSheetRepository balanceSheetRepository;

    @Override
    @Transactional
    public void execute(Integer customerId, List<LocalDate> monthYear) {
        this.balanceSheetRepository.deleteAllByCustomerIdAndMonthYearList(customerId, monthYear);
    }
}
