package com.dataimport.api.infra.controller.dto.customers;

import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.domain.Status;
import com.dataimport.api.domain.Customers;
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
    @NotNull
    @Email(message = "e-mail invalid!")
    private String email;
    private String phone;
    @NotNull
    private Status status;
    @NotNull
    private ReadModelVersion readModelVersion;


    public Customers toDomain(Integer id) {
        return Customers.builder()
                .id(id)
                .name(this.getName())
                .cnpj(this.getCnpj())
                .address(this.getAddress())
                .number(this.getNumber())
                .complement(this.getComplement())
                .city(this.getCity())
                .state(this.getState())
                .zipCode(this.getZipCode())
                .email(this.getEmail())
                .phone(this.getPhone())
                .status(this.getStatus())
                .readModelVersion(this.getReadModelVersion())
                .build();
    }

}
