package com.rentcar.product.service;

import com.rentcar.product.entity.ContractRent;
import com.rentcar.product.enums.EActive;
import com.rentcar.product.repository.ICarRentRepository;
import com.rentcar.product.repository.IContractRentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractRentService {

    private IContractRentRepository contractRentRepository;

    public ContractRentService(IContractRentRepository contractRentRepository) {
        this.contractRentRepository = contractRentRepository;
    }

    public List<ContractRent> findContractsByCarId(long id) {
        return contractRentRepository.findContractsByCarId(id);
    }

    public ContractRent createOrUpdate(ContractRent entity) {
        // validate if dat init and dat final entity have available to conclude a contract before of save
        return contractRentRepository.save(entity);
    }

    public void delete(Long id) {
        // validate if contract was paid to delete
        contractRentRepository.deleteById(id);
    }


}
