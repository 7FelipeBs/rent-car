package com.rentcar.product.service;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.repository.ICarRentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarRentService {

    private final ICarRentRepository carRentRepository;
    private final ContractRentService contractRentService;

    public CarRentService(ICarRentRepository carRentRepository, ContractRentService contractRentService) {
        this.carRentRepository = carRentRepository;
        this.contractRentService = contractRentService;
    }

    public List<CarRent> findByAllCars() {
        var cars = carRentRepository.findAll();
        cars.forEach(x -> x.setContractRentList(contractRentService.findContractsByCarId(x.getId())));

        return cars;
    }

    public CarRent createOrUpdate(CarRent entity) {
        // validate if dat init and dat final entity have available to conclude a contract before of save
        return carRentRepository.save(entity);
    }

    public void delete(Long id) {
        // validate if contract was paid to delete
        carRentRepository.deleteById(id);
    }
}
