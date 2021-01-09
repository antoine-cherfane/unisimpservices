/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.controllers;

import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.elemenopi.unisimpservices.models.ErrorResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Elemenopi
 */
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {
    
    @ExceptionHandler({GenericException.class, UsernameNotFoundException.class})
    public Object genericExceptionHandler(Exception e, WebRequest request) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
