package com.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResultException.class)
    @ResponseBody
    public String noResultExceptionHandler() {
        System.out.println("------------From Exception Handler Controller : NoResultException");
        return "Sorry. No Result Exception: No entity found for query";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public String numberFormatExceptionHandler() {
        System.out.println("------------From Exception Handler Controller : NumberFormatException");
        return "Sorry. Number Format Exception.";
    }

}
