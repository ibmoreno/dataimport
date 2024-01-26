package com.dataimport.api.domain;

import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntityPk;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceSheet {
    private Integer customersId;
    private Integer accountingAccountsId;
    private LocalDate monthYear;
    private BigDecimal costValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BalanceSheetEntity toEntity() {
        BalanceSheetEntityPk balanceSheetEntityPk = BalanceSheetEntityPk.builder()
                .accountingAccountsId(accountingAccountsId)
                .customersId(customersId)
                .monthYear(monthYear)
                .build();

        return BalanceSheetEntity.builder()
                .balanceSheetEntityPk(balanceSheetEntityPk)
                .costValue(costValue)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}