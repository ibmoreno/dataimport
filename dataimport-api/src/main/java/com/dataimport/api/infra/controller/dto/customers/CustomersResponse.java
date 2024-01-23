package com.dataimport.api.infra.controller.dto.customers;

import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.domain.Status;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersResponse {
    private Integer id;
    private String name;
    private String cnpj;
    private String address;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String phone;
    private Status status;
    private ReadModelVersion readModelVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}