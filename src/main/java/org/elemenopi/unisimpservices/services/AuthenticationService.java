/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Optional;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.models.AuthenticationRequest;
import org.elemenopi.unisimpservices.models.AuthenticationResponse;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.elemenopi.unisimpservices.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class AuthenticationService {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    PasswordEncoderService passwordEncoderService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ObjectMapper mapper;
    
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if(!passwordEncoderService.verify(authenticationRequest.getPassword(), userDetails.getPassword()))
            throw new GenericException("Wrong credentials !");
        
        Optional<User> optionalUser = userRepository.findByUsername(authenticationRequest.getUsername());
        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException("Wrong credentials !");
        User user = optionalUser.get();
        
        return new AuthenticationResponse(jwtUtil.generateToken(userDetails), user);
    }
    
    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) throws Exception {
        ObjectNode userResponse = userService.saveUser(authenticationRequest);
        return mapper.readValue(userResponse.toString(), AuthenticationResponse.class);
    }
}
