package com.rentcar.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
