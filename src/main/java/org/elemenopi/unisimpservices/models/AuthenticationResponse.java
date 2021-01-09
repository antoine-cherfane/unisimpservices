/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.models;

import java.io.Serializable;
import lombok.Data;
import org.elemenopi.unisimpservices.entities.User;

/**
 *
 * @author Elemenopi
 */
@Data
public class AuthenticationResponse implements Serializable {
    String token;
    User User;

    public AuthenticationResponse(String token, User User) {
        this.token = token;
        this.User = User;
    }
}
