package com.dataimport.api.domain;

import com.dataimport.api.infra.controller.dto.accounting_accounts.AccountingAccountsResponse;
import com.dataimport.api.infra.controller.dto.accounting_accounts.AggregateAccountsResponse;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountingAccounts {
    private Integer id;
    private String description;
    private AggregateAccount aggregateAccount;
    private Boolean active;
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdAt;
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime updatedAt;

    public void removeAggregateAccount() {
        aggregateAccount= null;
    }

    public AccountingAccountsEntity toEntity() {
        return AccountingAccountsEntity.builder()
                .id(id)
                .description(description)
                .aggregateAccount(toEntity(aggregateAccount))
                .status(Boolean.TRUE.equals(active) ? Status.A : Status.I)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    private AccountingAccountsEntity toEntity(AggregateAccount aggregateAccount) {
        if (aggregateAccount == null) {
            return null;
        }
        return aggregateAccount.toEntity();
    }

    public AccountingAccountsResponse toResponse() {
        return AccountingAccountsResponse.builder()
                .id(id)
                .description(description)
                .aggregateAccount(toResponse(aggregateAccount))
                .active(active)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    private AggregateAccountsResponse toResponse(AggregateAccount aggregateAccount) {
        if (aggregateAccount == null) {
            return null;
        }
        return aggregateAccount.toResponse();
    }
}