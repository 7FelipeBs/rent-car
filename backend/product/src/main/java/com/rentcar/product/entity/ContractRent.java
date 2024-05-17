package com.rentcar.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "CONTRACT_RENT")
public class ContractRent {

    @Id
    @Column(name = "CONTRACT_RENT_ID")
    private Long id;

    @Column(name = "CAR_RENT_ID")
    private Long carRentId;

    @Column(name = "VALUE_RENT")
    private Double valueRent;

    @Column(name = "INIT_CONTRACT_DAT")
    private LocalDateTime initContractDat;

    @Column(name = "FINAL_CONTRACT_DAT")
    private LocalDateTime finalContractDat;

    @Column(name = "CONTRACT_PAID")
    private String contractPaid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractRent that = (ContractRent) o;
        return Objects.equals(id, that.id) && Objects.equals(carRentId, that.carRentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carRentId);
    }
}
