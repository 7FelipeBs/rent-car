package com.rentcar.product.repository;

import com.rentcar.product.entity.CarRent;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ICarRentRepository extends JpaRepository<CarRent, Long> {

}
