package com.dataimport.api;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/customers")
@RequiredArgsConstructor
@Validated
public class CustomersController {

    private final ImportDataToBalanceSheetService importDataToBalanceSheetService;
    private final GetAllCustomers getAllCustomers;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<CustomersResponse>> getAll(Pageable pageable) {
        Page<CustomersResponse> customers = getAllCustomers.execute(pageable);
        return ResponseEntity.ok().body(customers);
    }

    @PostMapping("/{customerId}/import/{year}")
    public ResponseEntity<Map<String, String>> importMovementAccount(@PathVariable Integer customerId,
                                                                     @PathVariable Integer year,
                                                                     @RequestParam @Valid List<@Range(min = 1, max = 12,
                                                                             message = "Mes inválido") Integer> months,
                                                                     @RequestParam MultipartFile file) {

        importDataToBalanceSheetService.execute(customerId, year, months, UploadFileHelper.isValidFile(file));
        return ResponseEntity.ok(Map.of("message", "Imported successfully"));

    }

}
