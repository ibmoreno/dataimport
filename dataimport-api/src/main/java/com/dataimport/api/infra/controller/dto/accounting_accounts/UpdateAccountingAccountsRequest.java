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
public class UpdateAccountingAccountsRequest {
    @NotNull(message = "description cannot be null!")
    private String description;
    private AggregateAccountsResponse aggregateAccount;
    private boolean active;

    public AccountingAccounts toDomain(AccountingAccounts accountingAccounts) {
        return AccountingAccounts.builder()
                .id(accountingAccounts.getId())
                .description(description)
                .aggregateAccount(toDomain(aggregateAccount))
                .active(active)
                .createdAt(accountingAccounts.getCreatedAt())
                .updatedAt(accountingAccounts.getUpdatedAt())
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
