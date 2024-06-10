package com.rentcar.product.service;

import com.rentcar.product.entity.ContractRent;
import com.rentcar.product.exception.handler.BusinessRuleException;
import com.rentcar.product.repository.IContractRentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContractRentService {

    private final IContractRentRepository contractRentRepository;

    public ContractRentService(IContractRentRepository contractRentRepository) {
        this.contractRentRepository = contractRentRepository;
    }

    public List<ContractRent> findContractsByCarId(long id) {
        log.info("find by contract with id car rent");
        return contractRentRepository.findContractsByCarId(id);
    }

    public ContractRent createOrUpdate(ContractRent entity) {
        validateEntity(entity);
        validateContractPeriod(entity);

        if(entity.getContractPaid() == null) entity.setContractPaid("N");

        log.info("save or update contract");
        return contractRentRepository.save(entity);
    }

    public void delete(Long id) {
        if(!validateContractPay(id)) {
            throw new BusinessRuleException("It was not possible to delete due to having contracts with financial pending!");
        }

        log.info("delete contract by id");
        contractRentRepository.deleteById(id);
    }

    private void validateEntity(ContractRent entity) {
        if(entity == null) {
            log.debug("Contract is null");
            throw new BusinessRuleException("Contract cannot null!");
        }

        if(entity.getCarRentId() == null) {
            log.error("Contract Without Id Car");
            throw new BusinessRuleException("Contract need a car!");
        }

        if(entity.getInitContractDat() == null || entity.getFinalContractDat() == null) {
            log.error("Init Contract Or Final Contract is null");
            throw new BusinessRuleException("Invalid period contract!");
        }

        if(entity.getValueRent() == null) {
            log.error("Value Contract is null!");
            throw new BusinessRuleException("Invalid value contract!");
        }

    }


    private void validateContractPeriod(ContractRent entity) {
       var entityExist = contractRentRepository.findByContractDat(entity.getInitContractDat(), entity.getCarRentId());
       if(entityExist != null) {
           if(entity.getId() == null) {
               throw new BusinessRuleException("Exist a period contract with these init date");
           }

           if(entity.getId().equals(entityExist.getId())) {
               throw new BusinessRuleException("Exist a period contract with these init date");
           }
       }
    }

    private boolean validateContractPay(Long id) {
        log.debug("Find by contract by id");
        var contract = contractRentRepository.findById(id).orElse(null);
        if(contract == null) {
            throw new BusinessRuleException("Don't find contract with these id");
        }

        log.debug("Verify if contract was paid");
        return contract.getContractPaid().equals("S");
    }
}
