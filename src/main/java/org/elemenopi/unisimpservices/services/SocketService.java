/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.*;
import org.elemenopi.unisimpservices.entities.Message;
import org.elemenopi.unisimpservices.entities.Relation;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.repositories.RelationRepository;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class SocketService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    SimpMessagingTemplate socket;
    
    public void sendSocket(Message message) throws Exception {
        socket.convertAndSend("/chat/" + message.getIdUserTo(), message);
    }
}
