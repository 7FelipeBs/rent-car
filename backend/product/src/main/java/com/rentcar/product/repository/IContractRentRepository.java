package com.rentcar.product.repository;

import com.rentcar.product.entity.ContractRent;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Hidden
public interface IContractRentRepository extends JpaRepository<ContractRent, Long> {

    @Query("""
            FROM ContractRent
            WHERE carRentId = :id
            """)
    List<ContractRent> findContractsByCarId(long id);

    @Query("""
            FROM ContractRent
            WHERE carRentId = :carRentId
                AND :initContractDat BETWEEN initContractDat OR finalContractDat
            """)
    ContractRent findByContractDat(LocalDateTime initContractDat, long carRentId);
}
