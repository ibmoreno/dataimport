package com.dataimport.api.infra.gateway;


import com.dataimport.api.application.gateway.BalanceSheetGateway;
import com.dataimport.api.domain.BalanceSheet;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.repository.BalanceSheetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceSheetRepositoryGateway implements BalanceSheetGateway {

    private final BalanceSheetRepository balanceSheetRepository;
    @Override
    public List<BalanceSheet> saveAll(List<BalanceSheet> balanceSheets) {
        List<BalanceSheetEntity> balanceSheetEntities = balanceSheets.stream().map(BalanceSheet::toEntity).toList();
        balanceSheetRepository.saveAll(balanceSheetEntities);
        return balanceSheetEntities.stream().map(BalanceSheetEntity::toDomain).toList();
    }
}
