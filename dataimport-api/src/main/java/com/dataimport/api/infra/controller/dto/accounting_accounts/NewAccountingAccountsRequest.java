package com.dataimport.api.infra.controller.dto.accounting_accounts;

import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.AggregateAccount;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountingAccountsRequest {
    @NotNull(message = "description cannot be null!")
    private String description;
    private AggregateAccountsResponse aggregateAccount;
    private boolean active;

    public AccountingAccounts toDomain() {
        return AccountingAccounts.builder()
                .aggregateAccount(toDomain(aggregateAccount))
                .description(description)
                .active(active)
                .build();
    }

    private AggregateAccount toDomain(AggregateAccountsResponse aggregateAccount) {
        if (aggregateAccount == null) {
            return null;
        }
        return AggregateAccount.builder()
                .id(aggregateAccount.getId())
                .description(aggregateAccount.getDescription())
                .build();
    }
}
