/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.controllers;


import javax.servlet.http.HttpServletRequest;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.services.UserService;
import org.elemenopi.unisimpservices.utils.FunctionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.elemenopi.unisimpservices.entities.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elemenopi
 */
@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @Autowired
    FunctionsUtil functionsUtil;
    
    @GetMapping(path = "/get-all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers(HttpServletRequest request) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(userService.getAllUsers(initiator).toString(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/get-all-filtered-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllFilteredUsers(HttpServletRequest request) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(userService.getAllFilteredUsers(initiator).toString(), HttpStatus.OK);
    }
    
    @PostMapping(path = "/save-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveUser(HttpServletRequest request, @RequestBody String body) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(userService.saveUser(initiator, body).toString(), HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/delete-user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(HttpServletRequest request, @PathVariable Integer userID) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        userService.deleteUser(initiator, userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

