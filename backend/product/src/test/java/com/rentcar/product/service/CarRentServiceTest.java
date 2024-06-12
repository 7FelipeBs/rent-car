package com.rentcar.product.service;

import com.rentcar.product.entity.CarRent;
import com.rentcar.product.exception.BusinessRuleException;
import com.rentcar.product.messaging.publisher.ProductPublisher;
import com.rentcar.product.repository.ICarRentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarRentServiceTest {

    @InjectMocks
    CarRentService carRentService;

    @Mock
    ICarRentRepository carRentRepository;

    @Mock
    private ProductPublisher productPublisher;

    CarRent carRentToSave;
    CarRent carRentExists;

    @BeforeEach
    public void setup () {
        carRentToSave = CarRent.builder()
                .id(null)
                .userId(1L)
                .model("Gol G5")
                .identification("NG9T613")
                .active("S")
                .build();

        carRentExists = CarRent.builder()
                .id(1L)
                .userId(1L)
                .model("Gol G9")
                .identification("NG9T613")
                .active("S")
                .build();
    }


    @Test
    void whenSaveCarRentWithSuccess() {
        when(carRentRepository.save(carRentToSave)).thenReturn(carRentToSave);
        when(carRentRepository.findByIdentification(carRentToSave.getIdentification())).thenReturn(null);

       CarRent carRentGenerated = carRentService.createOrUpdate(carRentToSave);

        assertEquals(carRentToSave, carRentGenerated);
        verify(carRentRepository).save(carRentToSave);
        verify(productPublisher).sendMessageWithObjectSuccess(carRentToSave);
        verifyNoMoreInteractions(carRentRepository);
    }

    @Test
    void whenSaveCarRentWithValidateError() {
        when(carRentRepository.findByIdentification(carRentToSave.getIdentification()))
                .thenReturn(carRentExists);

        final BusinessRuleException e = assertThrows(BusinessRuleException.class, () -> {
            carRentService.createOrUpdate(carRentToSave);
        });

        assertThat(e.getMessage(), is("There is a car with these identification!"));
        verify(carRentRepository).findByIdentification(carRentToSave.getIdentification());
        verifyNoMoreInteractions(carRentRepository);
    }

    @Test
    void whenSaveCarRentNullWithError() {
        final BusinessRuleException e = assertThrows(BusinessRuleException.class, () -> {
            carRentService.createOrUpdate(null);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("CarRent cannot null!"));
        verifyNoInteractions(carRentRepository);
    }
}