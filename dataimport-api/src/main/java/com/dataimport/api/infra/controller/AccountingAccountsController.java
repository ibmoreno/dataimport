package com.dataimport.api.infra.controller;

import com.dataimport.api.application.service.AccountingAccountsService;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.infra.controller.dto.accounting_accounts.AccountingAccountsResponse;
import com.dataimport.api.infra.controller.dto.accounting_accounts.NewAccountingAccountsRequest;
import com.dataimport.api.infra.controller.dto.accounting_accounts.UpdateAccountingAccountsRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/accounting-accounts")
@RequiredArgsConstructor
@Validated
public class AccountingAccountsController {

    private final AccountingAccountsService accountingAccountsService;

    @GetMapping
    public ResponseEntity<Page<AccountingAccountsResponse>> getAll(
            @SortDefault(sort = "description", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AccountingAccountsResponse> customers = accountingAccountsService
                .getAll(pageable).map(AccountingAccounts::toResponse);
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AccountingAccountsResponse>> search(
            @RequestParam(value = "search", required = false) String search,
            @SortDefault(sort = "description", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AccountingAccountsResponse> customers = accountingAccountsService
                .search(search, pageable).map(AccountingAccounts::toResponse);
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountingAccountsResponse> getOne(@PathVariable Integer id) {
        AccountingAccountsResponse customersResponse = accountingAccountsService.getOne(id).toResponse();
        return ResponseEntity.ok().body(customersResponse);
    }

    @PostMapping
    public ResponseEntity<AccountingAccountsResponse> save(@Valid @RequestBody NewAccountingAccountsRequest newAccountingAccountsRequest) {
        AccountingAccountsResponse customers = accountingAccountsService.create(newAccountingAccountsRequest).toResponse();
        return ResponseEntity.created(URI.create("/api/customers/" + customers.getId())).body(customers);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountingAccountsResponse> update(@PathVariable Integer id,
                                                             @Valid @RequestBody UpdateAccountingAccountsRequest updateAccountingAccountsRequest) {
        AccountingAccountsResponse customers = accountingAccountsService.update(id, updateAccountingAccountsRequest).toResponse();
        return ResponseEntity.ok().body(customers);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accountingAccountsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}