package com.bank.account.service.common;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Component
@ControllerAdvice
public class ExceptionReturner {

    public EntityNotFoundException getEntityNotFoundException(String message) {
        return new EntityNotFoundException(message);
    }
}
