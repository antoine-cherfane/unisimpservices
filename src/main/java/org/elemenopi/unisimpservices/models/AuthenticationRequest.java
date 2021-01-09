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
public class AuthenticationRequest implements Serializable {
    String username;
    String password;
}
