package com.shop.controllers;

import com.shop.services.CountryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;
import java.util.logging.Logger;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    private static final Logger log = Logger.getLogger(CountryServiceImpl.class.getName());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResultException.class)
    @ResponseBody
    public String noResultExceptionHandler() {
        log.warning("WARNING: Sorry. No Result Exception: No entity found for query");
        return "Sorry. No Result Exception: No entity found for query";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public String numberFormatExceptionHandler() {
        log.warning("WARNING: Sorry. Number Format Exception.");
        return "Sorry. Number Format Exception.";
    }

}
