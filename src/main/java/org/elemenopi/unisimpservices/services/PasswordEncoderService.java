/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class PasswordEncoderService {

    private BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encode(String value) {
        return getEncoder().encode(value);
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return getEncoder().matches(rawPassword, encodedPassword);
    }
}
