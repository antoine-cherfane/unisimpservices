/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elemenopi.unisimpservices.entities.User;

/**
 *
 * @author Elemenopi
 */
@Service
public class FunctionsUtil {
    
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    UserRepository UserRepository;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public User getInitiator(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        String username = jwtUtil.extractUsername(token);
        Optional<User> optionalUser = UserRepository.findByUsername(username);
        if(optionalUser.isPresent())
            return optionalUser.get();
        else
            return null;
    }
    
    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String allParts[] = authorizationHeader.split(" ");
        if(allParts.length != 2)
            return null;
        else
            return allParts[1];
    }
    
    public Boolean isEmptyString(String str) {
        return str == null || str.isBlank();
    }
    
    public String getDateStr() {
        return dateFormat.format(new Date());
    }
}
