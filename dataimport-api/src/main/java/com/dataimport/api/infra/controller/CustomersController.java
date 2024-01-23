package com.dataimport.api.infra.controller;

import com.dataimport.api.util.UploadFileHelper;
import com.dataimport.api.application.service.CustomersService;
import com.dataimport.api.domain.Customers;
import com.dataimport.api.infra.controller.dto.customers.CustomersResponse;
import com.dataimport.api.infra.controller.dto.customers.NewCustomersRequest;
import com.dataimport.api.infra.controller.dto.customers.UpdateCustomersRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/customers")
@RequiredArgsConstructor
@Validated
class CustomersController {

    private final CustomersService customersService;

    @GetMapping
    public ResponseEntity<Page<CustomersResponse>> getAll(Pageable pageable) {
        Page<CustomersResponse> customers = customersService.getAll(pageable).map(Customers::toResponse);
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomersResponse> getOne(@PathVariable Integer id) {
        CustomersResponse customersResponse = customersService.getOne(id).toResponse();
        return ResponseEntity.ok().body(customersResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> save(@Valid @RequestBody NewCustomersRequest newCustomersRequest) {
        CustomersResponse customers = customersService.create(newCustomersRequest).toResponse();
        return ResponseEntity.created(URI.create("/api/customers/" + customers.getId())).body(customers);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomersResponse> update(@PathVariable Integer id, @Valid @RequestBody UpdateCustomersRequest updateCustomersRequest) {
        CustomersResponse customers = customersService.update(id, updateCustomersRequest).toResponse();
        return ResponseEntity.ok().body(customers);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customersService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/import/{year}")
    public ResponseEntity<Map<String, String>> importMovementAccount(@PathVariable Integer customerId,
                                                                     @PathVariable Integer year,
                                                                     @RequestParam @Valid List<@Range(min = 1, max = 12,
                                                                             message = "Mes inválido") Integer> months,
                                                                     @RequestParam MultipartFile file) {

        customersService.importMovementAccount(customerId, year, months, UploadFileHelper.isValidFile(file));
        return ResponseEntity.ok(Map.of("message", "Imported successfully"));

    }

}
