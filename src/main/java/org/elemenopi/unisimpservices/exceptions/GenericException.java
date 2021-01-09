/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.exceptions;

/**
 *
 * @author Elemenopi
 */
public class GenericException extends Exception {
    
    private String message;

    public GenericException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
