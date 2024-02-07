package com.dataimport.api.infra.controller.dto.customers;

import com.dataimport.api.domain.Customers;
import com.dataimport.api.domain.ReadModelVersion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCustomersRequest {
    @NotNull(message = "name cannot be null!")
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
    private boolean active;
    @NotNull(message = "readModelVersion cannot be null!")
    private ReadModelVersion readModelVersion;

    public Customers toDomain() {
        return Customers.builder()
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
                .build();
    }
}
