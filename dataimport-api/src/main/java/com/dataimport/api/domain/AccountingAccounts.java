package com.dataimport.api.domain;

import com.dataimport.api.infra.controller.dto.accounting_accounts.AccountingAccountsResponse;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountingAccounts {
    private Integer id;
    private String description;
    private Integer aggregateAccountId;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void removeAggregateAccount() {
        aggregateAccountId = null;
    }

    public AccountingAccountsEntity toEntity() {
        return AccountingAccountsEntity.builder()
                .id(id)
                .description(description)
                .aggregateAccountId(aggregateAccountId)
                .status(Boolean.TRUE.equals(active) ? Status.A : Status.I)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public AccountingAccountsResponse toResponse() {
        return AccountingAccountsResponse.builder()
                .id(id)
                .description(description)
                .aggregateAccountId(aggregateAccountId)
                .active(active)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}