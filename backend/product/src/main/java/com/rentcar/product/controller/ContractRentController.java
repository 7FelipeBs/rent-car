package com.rentcar.product.controller;

import com.rentcar.product.entity.ContractRent;
import com.rentcar.product.service.ContractRentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractRent")
@Tag(name = "Contract Rent Controller", description = "Controller to management Contract Rent")
public class ContractRentController {

    private final ContractRentService contractRentService;

    public ContractRentController(ContractRentService contractRentService) {
        this.contractRentService = contractRentService;
    }

    @GetMapping
    @Operation(summary = "EndPoint Issue", description = "Find contracts rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public List<ContractRent> findContractsByCarId(
            @Parameter(name = "id", description = "id RequestParam") @RequestParam Long id) {
        return contractRentService.findContractsByCarId(id);
    }

    @PostMapping
    @Operation(summary = "EndPoint Issue", description = "Create contract rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ContractRent> create(
            @Parameter(name = "entity", description = "ContractRent RequestBody") @RequestBody ContractRent entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contractRentService.createOrUpdate(entity));
    }

    @PutMapping
    @Operation(summary = "EndPoint Issue", description = "Update contract rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ContractRent> update(
            @Parameter(name = "entity", description = "ContractRent RequestBody") @RequestBody ContractRent entity) {
        return ResponseEntity.ok().body(contractRentService.createOrUpdate(entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "EndPoint Issue", description = "Delete contract rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public void delete(
            @Parameter(name = "id", description = "id PathVariable") @PathVariable Long id) {
        contractRentService.delete(id);
    }
}
