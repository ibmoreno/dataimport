package com.dataimport.api.infra.controller.dto.accounting_accounts;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountingAccountsResponse {
    private Integer id;
    private String description;
    private AggregateAccountsResponse aggregateAccount;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
