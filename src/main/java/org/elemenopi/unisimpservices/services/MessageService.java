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
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.repositories.MessageRepository;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.elemenopi.unisimpservices.utils.FunctionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class MessageService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    SocketService socketService;
    
    @Autowired
    FunctionsUtil functionsUtil;
    
    public ObjectNode getAllMessages(User initiator) throws Exception {
        if(initiator == null)
            throw new GenericException("Not allowed !");
        
        List<Message> allMessages = messageRepository.findByUserID(initiator.getIdUser());
        Set<Integer> allUserIDs = new HashSet<>();
        for(Message message : allMessages) {
            if(!message.getIdUserFrom().equals(initiator.getIdUser()))
                    allUserIDs.add(message.getIdUserFrom());
			
            if(!message.getIdUserTo().equals(initiator.getIdUser()))
				allUserIDs.add(message.getIdUserTo());
        }
        
        List<User> allUsers = userRepository.findAllById(allUserIDs);
        
        ObjectNode response = mapper.createObjectNode();
        
        response.set("messages", mapper.convertValue(allMessages, ArrayNode.class));
        response.set("users", mapper.convertValue(allUsers, ArrayNode.class));
        
        return response;
    }
    
    public ObjectNode sendMessage(User initiator, String body) throws Exception {
        Message message = mapper.readValue(body, Message.class);
        validateMessage(initiator, message);
        
        message = messageRepository.save(message);
        socketService.sendSocket(message);
        
        return mapper.convertValue(message, ObjectNode.class);
    }
    
    private void validateMessage(User initiator, Message message) throws Exception {
        if(initiator == null || (message.getIdUserFrom() != null && !message.getIdUserFrom().equals(initiator.getIdUser())))
            throw new GenericException("Not allowed !");
        
        if(message.getIdUserTo() == null)
            throw new GenericException("User to required !");
        
        if(functionsUtil.isEmptyString(message.getText()))
            throw new GenericException("Message can't be empty !");
    }
}
