package com.rentcar.product.service;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.messaging.publisher.ProductPublisher;
import com.rentcar.product.repository.ICarRentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarRentService {

    private final ICarRentRepository carRentRepository;
    private final ContractRentService contractRentService;
    private final ProductPublisher productPublisher;

    public CarRentService(ICarRentRepository carRentRepository, ContractRentService contractRentService, ProductPublisher productPublisher) {
        this.carRentRepository = carRentRepository;
        this.contractRentService = contractRentService;
        this.productPublisher = productPublisher;
    }

    public List<CarRent> findByAllCars() {
        log.info("find by all car rent");
        var cars = carRentRepository.findAll();

        log.info("find by contract to each car rent if exist");
        cars.forEach(x -> x.setContractRentList(contractRentService.findContractsByCarId(x.getId())));

        return cars;
    }

    public CarRent createOrUpdate(CarRent entity) {
        // validate if dat init and dat final entity have available to conclude a contract before of save
        log.info("Create/Update entity");
        entity = carRentRepository.save(entity);

        log.info("Car Rent Publisher With Success");
        productPublisher.sendMessageWithObjectSuccess(entity);
        return entity;
    }

    public void delete(Long id) {
        // validate if contract was paid to delete
        try{
            log.info("trying delete by id");
            carRentRepository.deleteById(id);

            log.info("Car Rent Delete Publisher With Success");
            productPublisher.sendMessageWithObjectSuccess(id);
        } catch(Exception e) {
           log.error(e.getMessage());
        }
    }
}
