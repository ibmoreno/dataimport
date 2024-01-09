package com.dataimport.api;

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

    public static CustomersResponse fromEntity(Customers customers) {
        return CustomersResponse.builder()
                .id(customers.getId())
                .name(customers.getName())
                .cnpj(customers.getCnpj())
                .address(customers.getAddress())
                .number(customers.getNumber())
                .complement(customers.getComplement())
                .city(customers.getCity())
                .state(customers.getState())
                .zipCode(customers.getZipCode())
                .email(customers.getEmail())
                .phone(customers.getPhone())
                .status(customers.getStatus())
                .readModelVersion(customers.getReadModelVersion())
                .createdAt(customers.getCreatedAt())
                .updatedAt(customers.getUpdatedAt()).build();
    }
}