package com.rentcar.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR_RENT")
public class CarRent {

    @Id
    @Column(name = "CAR_RENT_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "IDENTIFICATION")
    private String identification;

    @Column(name = "ACTIVE")
    private String active;

    @Transient
    private List<ContractRent> contractRentList;
}
