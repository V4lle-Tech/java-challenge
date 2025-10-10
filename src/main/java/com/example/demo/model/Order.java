package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntityUUID {

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

}
