package com.startup.scrumboard.controller;

import com.startup.scrumboard.model.dto.ErrorDto;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j
public class ExceptionController {
    private static final String DEFAULT_TEXT = "Произошла ошибка на сервере!";


    private ErrorDto handleValidation(MethodArgumentNotValidException ex) {
        ErrorDto error = new ErrorDto();
        final StringBuilder message = new StringBuilder();
        if (ex.getBindingResult() != null &&
                ex.getBindingResult().hasErrors()) {
            ex.getBindingResult().getAllErrors().forEach(x -> message.append(x.getDefaultMessage()).append("\n\n"));
        }

        if (message.length() == 0) {
            message.append(DEFAULT_TEXT);
        }
        error.setDescription(message.toString());
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleError(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex, ex);
        ErrorDto error;
        if (ex instanceof MethodArgumentNotValidException) {
            error = handleValidation((MethodArgumentNotValidException) ex);
        } else {
            error = new ErrorDto();
            String message = ex.getMessage();
            if (message == null || message.trim().isEmpty()) {
                message = DEFAULT_TEXT;
            }
            error.setDescription(message);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
