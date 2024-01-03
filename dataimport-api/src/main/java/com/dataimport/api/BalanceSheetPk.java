package com.dataimport.api;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BalanceSheetPk {
    @Column(name = "customers_id", nullable = false)
    private Integer customersId;
    @Column(name = "accounting_accounts_id", nullable = false)
    private Integer accountingAccountsId;
    @Column(name = "month_year", nullable = false)
    private LocalDate monthYear;
}
