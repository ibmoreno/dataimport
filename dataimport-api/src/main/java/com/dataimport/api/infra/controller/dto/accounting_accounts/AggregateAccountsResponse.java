package com.dataimport.api.infra.controller.dto.accounting_accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AggregateAccountsResponse {
    private Integer id;
    private String description;
}
