/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.elemenopi.unisimpservices.entities.*;
import java.util.Optional;
import java.util.Set;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.models.AuthenticationRequest;
import org.elemenopi.unisimpservices.repositories.*;
import org.elemenopi.unisimpservices.utils.FunctionsUtil;
import org.elemenopi.unisimpservices.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class UserService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RelationRepository relationRepository;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    PasswordEncoderService passwordEncoderService;
    
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    FunctionsUtil functionsUtil;
    
    @Autowired
    ParameterRepository parameterRepository;
    
    @Autowired
    CalendarRepository calendarRepository;
    
    @Autowired
    CalendarElementRepository calendarElementRepository;
    
    public ArrayNode getAllUsers(User initiator) throws Exception {
        if(initiator == null)
            throw new GenericException("Not allowed !");
        
        return mapper.convertValue(userRepository.findAll(), ArrayNode.class);
    }
    
    public ArrayNode getAllFilteredUsers(User initiator) throws Exception {
        if(initiator == null)
            throw new GenericException("Not allowed !");
        
        List<Relation> allRelations = relationRepository.findByUserID(initiator.getIdUser());
        
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for(User user : allUsers) {
            boolean found = false;
            for(Relation relation : allRelations)
                if(relation.getIdUserFrom().equals(user.getIdUser()) || relation.getIdUserTo().equals(user.getIdUser())) {
                    found = true;
                    break;
                }
            
            if(!found)
                filteredUsers.add(user);
        }
        
        return mapper.convertValue(filteredUsers, ArrayNode.class);
    }
    
    public ObjectNode saveUser(User initiator, AuthenticationRequest authenticationRequest) throws Exception {
        User user = new User();
        user.setUsername(authenticationRequest.getUsername());
        
        String password = authenticationRequest.getPassword();
        String encodedPassword = passwordEncoderService.encode(password);
        user.setPassword(encodedPassword);
        
        user = userRepository.save(user);
        
        ObjectNode response = mapper.createObjectNode();
        response.set("user", mapper.convertValue(user, ObjectNode.class));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        response.set("token", mapper.convertValue(jwtUtil.generateToken(userDetails), JsonNode.class));
        
        return response;
    }
    
    public ObjectNode saveUser(AuthenticationRequest authenticationRequest) throws Exception {
        User user = new User();
        user.setUsername(authenticationRequest.getUsername());
        user.setPassword(authenticationRequest.getPassword());
        return saveUser(null, user);
    }
    
    public ObjectNode saveUser(User initiator, String body) throws Exception {
        User user = mapper.readValue(body, User.class);
        return saveUser(initiator, user);
    }
    
    public ObjectNode saveUser(User initiator, User user) throws Exception {
        validateUser(initiator, user);
        
        Boolean encode = true;
        if(user.getIdUser() != null) {
            Optional<User> optionalDbUser = userRepository.findById(user.getIdUser());
            if(optionalDbUser.isPresent()) {
                User dbUser = optionalDbUser.get();
                if(dbUser.getPassword() != null && dbUser.getPassword().equals(user.getPassword())) {
                    encode = false;
                }
            }
        }
        
        if(encode) {
            String encodedPassword = passwordEncoderService.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        
        if(user.getIdUser() == null)
            user.setCreatedAt(functionsUtil.getDateStr());
        
        user = userRepository.save(user);
        
        ObjectNode response = mapper.createObjectNode();
        response.set("user", mapper.convertValue(user, ObjectNode.class));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        response.set("token", mapper.convertValue(jwtUtil.generateToken(userDetails), JsonNode.class));
        
        return response;
    }
    
    public void deleteUser(User initiator, Integer userID) throws Exception {
        if(initiator == null || !userID.equals(initiator.getIdUser())) {
            throw new GenericException("Not allowed !");
        }
        
        Optional<User> optionalUser = userRepository.findById(userID);
        if(optionalUser.isEmpty()) return;
        User user = optionalUser.get();
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    private void validateUser(User initiator, User user) throws Exception {
        if(initiator != null && (user.getIdUser() != null && !user.getIdUser().equals(initiator.getIdUser())))
            throw new GenericException("Not allowed !");
        
        if(functionsUtil.isEmptyString(user.getUsername()))
            throw new GenericException("Username required !");
        
        if(functionsUtil.isEmptyString(user.getPassword()))
            throw new GenericException("Password required !");
        
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if(optionalUser.isPresent()) {
            User otherUser = optionalUser.get();
            if(!otherUser.getIdUser().equals(user.getIdUser())) {
                throw new GenericException("Username already taken !");  
            }
        }
    }
}
