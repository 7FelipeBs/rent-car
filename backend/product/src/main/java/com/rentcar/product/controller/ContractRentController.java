package com.rentcar.product.controller;

import com.rentcar.product.entity.ContractRent;
import com.rentcar.product.service.ContractRentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractRent")
public class ContractRentController {

    private final ContractRentService contractRentService;

    public ContractRentController(ContractRentService contractRentService) {
        this.contractRentService = contractRentService;
    }

    @GetMapping
    public List<ContractRent> findByAllCars(@RequestParam Long id) {
        return contractRentService.findContractsByCarId(id);
    }

    @PostMapping
    public ResponseEntity<ContractRent> create(@RequestBody ContractRent entity) {
        return ResponseEntity.ok().body(contractRentService.createOrUpdate(entity));
    }

    @PutMapping
    public ResponseEntity<ContractRent> update(@RequestBody ContractRent entity) {
        return ResponseEntity.ok().body(contractRentService.createOrUpdate(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contractRentService.delete(id);
    }
}
