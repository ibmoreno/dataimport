package com.dataimport.api.infra.database.jpa.entity;

import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounting_accounts")
@SequenceGenerator(name = "g_accounting_accounts", sequenceName = "seq_accounting_accounts", allocationSize = 1)
public class AccountingAccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "g_accounting_accounts")
    @Column(name = "id")
    private Integer id;
    @Column(name = "description", nullable = false)
    private String description;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.A;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public AccountingAccounts toDomain() {
        return AccountingAccounts.builder()
                .id(id)
                .description(description)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}
