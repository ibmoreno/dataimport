package com.dataimport.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataOutput {
    private Integer accountingAccountsId;
    private LocalDate monthYear;
    private BigDecimal value;
}
