package com.bank.antifraud.service;

import com.bank.antifraud.service.common.ExceptionReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionReturnerTest {

    @DisplayName("Проверка на срабатывание и выдачу конкретного сообщения исключения, позитивный сценарий")
    @Test
    void getEntityNotFoundExceptionTest () {
        ExceptionReturner exceptionReturner = new ExceptionReturner();
        String expectedMessage = "Entity not found";
        EntityNotFoundException entityNotFoundException = exceptionReturner.getEntityNotFoundException(expectedMessage);
        assertEquals(expectedMessage, entityNotFoundException.getMessage() );
    }
}
