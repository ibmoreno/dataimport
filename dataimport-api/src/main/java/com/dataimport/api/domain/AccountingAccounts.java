package com.dataimport.api.domain;

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
    @Builder.Default
    private Status status = Status.A;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccountingAccountsEntity toEntity() {
        return AccountingAccountsEntity.builder()
                .id(id)
                .description(description)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}