//package com.rentcar.product.service;
//
//import com.rentcar.product.entity.ContractRent;
//import com.rentcar.product.repository.IContractRentRepositoryTest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//public class ContractRentServiceTest {
//
//    private final IContractRentRepositoryTest contractRentRepository;
//
//    public ContractRentServiceTest(IContractRentRepositoryTest contractRentRepository) {
//        log.info("find by all contract rent");
//        this.contractRentRepository = contractRentRepository;
//    }
//
//    public List<ContractRent> findContractsByCarId(long id) {
//        log.info("find by contract rent with id car rent");
//        return contractRentRepository.findContractsByCarId(id);
//    }
//
//    public ContractRent createOrUpdate(ContractRent entity) {
//        // validate if dat init and dat final entity have available to conclude a contract before of save
//        log.info("save or update contract rent");
//        return contractRentRepository.save(entity);
//    }
//
//    public void delete(Long id) {
//        // validate if contract was paid to delete
//        log.info("delete by id");
//        contractRentRepository.deleteById(id);
//    }
//
//
//}
