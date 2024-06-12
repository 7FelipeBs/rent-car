package com.rentcar.product.service;

import com.rentcar.product.entity.ContractRent;
import com.rentcar.product.exception.BusinessRuleException;
import com.rentcar.product.repository.IContractRentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractRentServiceTest {

    @InjectMocks
    ContractRentService contractRentService;

    @Mock
    IContractRentRepository contractRentRepository;

    ContractRent contractRent;
    ContractRent contractRentWithExistingPeriod;

    @BeforeEach
    void start() {
        contractRent = ContractRent.builder()
                .id(null)
                .carRentId(1L)
                .valueRent(BigDecimal.valueOf(100.0))
                .initContractDat(LocalDateTime.now())
                .finalContractDat(LocalDateTime.now().plusDays(1))
                .contractPaid("N")
                .build();

        contractRentWithExistingPeriod = ContractRent.builder()
                .id(1L)
                .carRentId(1L)
                .valueRent(BigDecimal.valueOf(100.0))
                .initContractDat(contractRent.getInitContractDat().plusHours(2))
                .finalContractDat(LocalDateTime.now().plusDays(3))
                .contractPaid("N")
                .build();
    }


    @Test
    void whenSaveWithSuccess() {
        when(contractRentRepository.save(contractRent)).thenReturn(contractRent);
        when(contractRentRepository.findByContractDat(
                        contractRent.getInitContractDat(),
                        contractRent.getCarRentId())).thenReturn(null);


        ContractRent contractGenerated = contractRentService.createOrUpdate(contractRent);

        assertEquals(contractRent, contractGenerated);
        verify(contractRentRepository).save(contractRent);
        verifyNoMoreInteractions(contractRentRepository);
    }

    @Test
    void whenSaveWithValidateError() {
        when(contractRentRepository.findByContractDat(
                contractRent.getInitContractDat(),
                contractRent.getCarRentId())).thenReturn(contractRentWithExistingPeriod);

        final BusinessRuleException e = assertThrows(BusinessRuleException.class, () -> {
            contractRentService.createOrUpdate(contractRent);
        });

        assertThat(e.getMessage(), is("Exist a period contract with these init date"));
        verify(contractRentRepository).findByContractDat(
                contractRent.getInitContractDat(),
                contractRent.getCarRentId());

        verifyNoMoreInteractions(contractRentRepository);
    }

    @Test
    void whenSaveNullWithError() {
        final BusinessRuleException e = assertThrows(BusinessRuleException.class, () -> {
            contractRentService.createOrUpdate(null);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Contract cannot null!"));
        verifyNoInteractions(contractRentRepository);
    }

    @Test
    void whenSaveWithErrorOfEntityInvalidAttribute() {
        ContractRent contractRent = ContractRent.builder().build();

        final BusinessRuleException e = assertThrows(BusinessRuleException.class, () -> {
            contractRentService.createOrUpdate(contractRent);
        });

        assertThat(e, notNullValue());
        verifyNoInteractions(contractRentRepository);
    }
}
