package com.rentcar.product.controller;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.service.CarRentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carRent")
public class CarRentController {

    private final CarRentService carRentService;

    public CarRentController(CarRentService carRentService) {
        this.carRentService = carRentService;
    }

    @GetMapping("/findByAll")
    public List<CarRent> findByAllCars() {
        return carRentService.findByAllCars();
    }

    @PostMapping
    public ResponseEntity<CarRent> create(@RequestBody CarRent entity) {
        return ResponseEntity.ok().body(carRentService.createOrUpdate(entity));
    }

    @PutMapping
    public ResponseEntity<CarRent> update(@RequestBody CarRent entity) {
        return ResponseEntity.ok().body(carRentService.createOrUpdate(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carRentService.delete(id);
    }
}
