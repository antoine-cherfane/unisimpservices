/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.controllers;


import javax.servlet.http.HttpServletRequest;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.services.MessageService;
import org.elemenopi.unisimpservices.services.ParameterService;
import org.elemenopi.unisimpservices.utils.FunctionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/message")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    FunctionsUtil functionsUtil;
    
    @GetMapping(path = "/get-all-messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllMessages(HttpServletRequest request) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(messageService.getAllMessages(initiator).toString(), HttpStatus.OK);
    }
    
    @PostMapping(path = "/send-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendMessage(HttpServletRequest request, @RequestBody String body) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(messageService.sendMessage(initiator, body).toString(), HttpStatus.OK);
    }
}

