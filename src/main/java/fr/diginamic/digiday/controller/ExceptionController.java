package fr.diginamic.digiday.controller;

import fr.diginamic.digiday.exceptions.DigidayWebApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DigidayWebApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> onDigidayWebApiException(DigidayWebApiException ex) {
	    return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> generaleException(Exception ex) {
        return Map.of("error", ex.getMessage());
    }
}
