package com.dataimport.api.infra.controller.dto.accounting_accounts;


import com.dataimport.api.domain.AccountingAccounts;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountingAccountsRequest {
    @NotNull(message = "description cannot be null!")
    private String description;
    private Integer aggregateAccountId;
    private boolean active;

    public AccountingAccounts toDomain(Integer id) {
        return AccountingAccounts.builder()
                .id(id)
                .description(description)
                .aggregateAccountId(aggregateAccountId)
                .active(active)
                .build();
    }

}
