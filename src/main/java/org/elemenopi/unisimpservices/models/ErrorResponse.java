/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.models;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Elemenopi
 */
@Data
public class ErrorResponse implements Serializable {
    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
