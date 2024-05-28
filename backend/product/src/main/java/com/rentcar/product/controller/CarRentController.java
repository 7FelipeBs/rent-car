package com.rentcar.product.controller;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.service.CarRentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carRent")
@OpenAPIDefinition(
        info = @Info(
                title = "Car Rent Controller",
                description = "Controller to management Car Rent"
        ))
public class CarRentController {
    private final CarRentService carRentService;
    public CarRentController(CarRentService carRentService) {
        this.carRentService = carRentService;
    }

    @GetMapping("/findByAll")
    @Operation(
            summary = "Endpoint issue",
            description = "Find all car rent",
            tags = {
                    "issues"
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get with success."),
                    @ApiResponse(responseCode = "400", description = "Error when trying find all car rent.")
            }
    )
    public ResponseEntity<List<CarRent>> findByAllCars() {
        return ResponseEntity.ok().body(carRentService.findByAllCars());
    }

    @PostMapping
    @Operation(
            summary = "Endpoint issue",
            description = "Create car rent",
            tags = {
                    "issues"
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created with success."),
                    @ApiResponse(responseCode = "400", description = "Error when trying created a car rent.")
            }
    )
    public ResponseEntity<CarRent> create(@RequestBody CarRent entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carRentService.createOrUpdate(entity));
    }

    @PutMapping
    @Operation(
            summary = "Endpoint issue",
            description = "Update car rent",
            tags = {
                    "issues"
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Update with success."),
                    @ApiResponse(responseCode = "400", description = "Error when trying updated car rent.")
            }
    )
    public ResponseEntity<CarRent> update(@RequestBody CarRent entity) {
        return ResponseEntity.ok().body(carRentService.createOrUpdate(entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Endpoint issue",
            description = "Update car rent",
            tags = {
                    "issues"
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete with success."),
                    @ApiResponse(responseCode = "400", description = "Error when trying deleted car rent.")
            }
    )
    public void delete(@PathVariable Long id) {
        carRentService.delete(id);
    }
}
