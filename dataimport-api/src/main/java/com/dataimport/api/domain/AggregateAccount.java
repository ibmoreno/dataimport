package com.dataimport.api.domain;

import com.dataimport.api.infra.controller.dto.accounting_accounts.AggregateAccountsResponse;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggregateAccount {
    private Integer id;
    private String description;

    public AccountingAccountsEntity toEntity() {
        if (id == null) {
            return null;
        }
        return AccountingAccountsEntity.builder()
                .id(id)
                .description(description)
                .build();
    }

    public AggregateAccountsResponse toResponse() {
        if (id == null) {
            return null;
        }
        return AggregateAccountsResponse.builder()
                .id(id)
                .description(description)
                .build();
    }
}
