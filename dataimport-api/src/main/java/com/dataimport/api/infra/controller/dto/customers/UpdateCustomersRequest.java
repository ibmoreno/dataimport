package com.dataimport.api.infra.controller.dto.customers;

import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.ReadModelVersion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomersRequest {
    @NotNull
    private String name;
    @CNPJ
    private String cnpj;
    private String address;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    @Email(message = "e-mail invalid!")
    private String email;
    private String phone;
    @NotNull
    private Boolean active;
    @NotNull
    private ReadModelVersion readModelVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customers toDomain(Integer id) {
        return Customers.builder()
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
