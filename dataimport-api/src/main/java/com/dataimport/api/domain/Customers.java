package com.dataimport.api.domain;

import com.dataimport.api.infra.database.jpa.entity.CustomersEntity;
import com.dataimport.api.infra.controller.dto.customers.CustomersResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
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
    private Boolean active;
    private ReadModelVersion readModelVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomersEntity toEntity() {
        return CustomersEntity.builder()
                .id(id)
                .name(name)
                .cnpj(cnpj)
                .address(address)
                .number(number)
                .complement(complement)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .email(email)
                .phone(phone)
                .status(Boolean.TRUE.equals(active) ? Status.A : Status.I)
                .readModelVersion(readModelVersion)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public CustomersResponse toResponse() {
        return CustomersResponse.builder()
                .id(id)
                .name(name)
                .cnpj(cnpj)
                .address(address)
                .number(number)
                .complement(complement)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .email(email)
                .phone(phone)
                .active(active)
                .readModelVersion(readModelVersion)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
