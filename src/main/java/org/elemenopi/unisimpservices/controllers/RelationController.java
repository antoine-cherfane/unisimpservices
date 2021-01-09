/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.controllers;


import javax.servlet.http.HttpServletRequest;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.services.RelationService;
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
@RequestMapping(value = "/relation")
@CrossOrigin(origins = "*")
public class RelationController {
    
    @Autowired
    RelationService relationService;
    
    @Autowired
    FunctionsUtil functionsUtil;
    
    @GetMapping(path = "/get-all-relations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRelations(HttpServletRequest request) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(relationService.getAllRelations(initiator).toString(), HttpStatus.OK);
    }
    
    @PostMapping(path = "/save-relation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveRelation(HttpServletRequest request, @RequestBody String body) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        return new ResponseEntity<>(relationService.saveRelation(initiator, body).toString(), HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/delete-relation/{relationID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRelation(HttpServletRequest request, @PathVariable Integer relationID) throws Exception {
        User initiator = functionsUtil.getInitiator(request);
        relationService.deleteRelation(initiator, relationID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

