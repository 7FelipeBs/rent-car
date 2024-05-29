package com.rentcar.product.controller;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.service.CarRentService;
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
@RequestMapping("/api/carRent")
@Tag(name = "Car Rent Controller", description = "Controller to management Car Rent")
public class CarRentController {
    private final CarRentService carRentService;
    public CarRentController(CarRentService carRentService) {
        this.carRentService = carRentService;
    }

    @GetMapping("/findByAll")
    @Operation(summary = "EndPoint Issue", description = "Find all car rent details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<List<CarRent>> findByAllCars() {
        return ResponseEntity.ok().body(carRentService.findByAllCars());
    }

    @PostMapping
    @Operation(summary = "EndPoint Issue", description = "Create car rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<CarRent> create(
            @Parameter(name = "CarRent", description = "CarRent RequestBody") @RequestBody CarRent entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carRentService.createOrUpdate(entity));
    }

    @PutMapping
    @Operation(summary = "EndPoint Issue", description = "Update car rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<CarRent> update(
            @Parameter(name = "CarRent", description = "CarRent RequestBody") @RequestBody CarRent entity) {
        return ResponseEntity.ok().body(carRentService.createOrUpdate(entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "EndPoint Issue", description = "Delete car rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public void delete(
            @Parameter(name = "id", description = "id PathVariable")  @PathVariable Long id) {
        carRentService.delete(id);
    }
}
