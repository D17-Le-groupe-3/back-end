package fr.diginamic.digiday.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.diginamic.digiday.exceptions.DigidayWebApiException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(DigidayWebApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String onDigidayWebApiException(DigidayWebApiException ex) {
	return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String generaleException(Exception ex) {
	return ex.getMessage();
    }
}
