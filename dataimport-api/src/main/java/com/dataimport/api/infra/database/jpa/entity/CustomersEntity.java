package com.dataimport.api.infra.database.jpa.entity;


import com.dataimport.api.domain.ReadModelVersion;
import com.dataimport.api.domain.Status;
import com.dataimport.api.domain.Customers;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE customers SET status = 'D' WHERE id = ?")
@Table(name = "customers")
@SequenceGenerator(name = "g_customers", sequenceName = "seq_customers", allocationSize = 1)
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "g_customers")
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "address")
    private String address;
    @Column(name = "number")
    private String number;
    @Column(name = "complement")
    private String complement;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.A;
    @Enumerated(EnumType.STRING)
    @Column(name = "read_model_version", nullable = false)
    private ReadModelVersion readModelVersion;
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Customers toDomain() {
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
                .status(status)
                .readModelVersion(readModelVersion)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }



}