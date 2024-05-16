package com.rentcar.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "CAR_RENT")
public class CarRent {

    @Id
    @Column(name = "CAR_RENT_ID")
    private Long id;

    @Column(name = "CONTRACT_RENT_ID")
    private Long contractRentId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "IDENTIFICATION")
    private String identification;

    @Column(name = "ACTIVE")
    private String active;
}
