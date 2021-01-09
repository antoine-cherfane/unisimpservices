/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.controllers;


import org.elemenopi.unisimpservices.services.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(value = "/parameter")
@CrossOrigin(origins = "*")
public class ParameterController {
    
    @Autowired
    ParameterService parameterService;
    
    @PostMapping(path = "/multiple-list-parameter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMultipleListParameter(@RequestBody String body) throws Exception {
        return new ResponseEntity<>(parameterService.getMultipleListParameter(body).toString(), HttpStatus.OK);
    }
}

