package com.rentcar.product.repository;

import com.rentcar.product.entity.CarRent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRentRepository extends JpaRepository<CarRent, Long> {

}
