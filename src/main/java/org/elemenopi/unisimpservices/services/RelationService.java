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
import org.elemenopi.unisimpservices.entities.Parameter;
import org.elemenopi.unisimpservices.entities.Relation;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.exceptions.GenericException;
import org.elemenopi.unisimpservices.repositories.ParameterRepository;
import org.elemenopi.unisimpservices.repositories.RelationRepository;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.elemenopi.unisimpservices.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class RelationService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    RelationRepository relationRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ParameterRepository parameterRepository;
    
    public ObjectNode getAllRelations(User initiator) throws Exception {
        if(initiator == null)
            throw new GenericException("Not allowed !");
        
        List<Relation> allRelations = relationRepository.findByUserID(initiator.getIdUser());
        Set<Integer> allUserIDs = new HashSet<>();
        for(Relation relation : allRelations) {
            allUserIDs.add(relation.getIdUserFrom());
            allUserIDs.add(relation.getIdUserTo());
        }
        
        List<User> allUsers = userRepository.findAllById(allUserIDs);
        
        ObjectNode response = mapper.createObjectNode();
        
        response.set("relations", mapper.convertValue(allRelations, ArrayNode.class));
        response.set("users", mapper.convertValue(allUsers, ArrayNode.class));
        
        return response;
    }
    
    public ObjectNode saveRelation(User initiator, String body) throws Exception {
        Relation relation  = mapper.readValue(body, Relation.class);
        validateRelation(initiator, relation);
        
        if(relation.getParamRelationType() != null) {
            Optional<Parameter> optionalParam = parameterRepository.findById(relation.getParamRelationType().getIdParameter());
            if(optionalParam.isPresent())
                relation.setParamRelationType(optionalParam.get());
        }
        
        Relation oldRelation = null;
        List<Relation> allRelation = relationRepository.findByUserID(initiator.getIdUser());
        for(Relation currentRelation : allRelation) {
            if((currentRelation.getIdUserTo().equals(relation.getIdUserTo()) && currentRelation.getIdUserFrom().equals(relation.getIdUserFrom()))
                    || (currentRelation.getIdUserTo().equals(relation.getIdUserFrom()) && currentRelation.getIdUserFrom().equals(relation.getIdUserTo()))) {
                oldRelation = currentRelation;
                break;
            }
        }
            
        if(oldRelation != null) {
            if(relation.getParamRelationType().getIdParameter().equals(ParamsUtil.relationTypeFriendRequestSent)
                    && oldRelation.getParamRelationType().getIdParameter().equals(ParamsUtil.relationTypeFriendRequestSent)
                    && oldRelation.getIdUserTo().equals(relation.getIdUserFrom())
                    && oldRelation.getIdUserFrom().equals(relation.getIdUserTo())) {
                Optional<Parameter> optionalParam = parameterRepository.findById(ParamsUtil.relationTypeFriends);
                if(optionalParam.isPresent())
                    relation.setParamRelationType(optionalParam.get());
            }
            
            relationRepository.deleteById(oldRelation.getIdRelation());
        }
        
        relation = relationRepository.save(relation);
        
        return mapper.convertValue(relation, ObjectNode.class);
    }
    
    public void deleteRelation(User initiator, Integer relationID) throws Exception {
        Optional<Relation> optionalRelation = relationRepository.findById(relationID);
        if(optionalRelation.isEmpty()) return;
        Relation relation = optionalRelation.get();
        
        if(initiator == null
                || relation.getIdUserFrom() == null
                || relation.getIdUserTo() == null
                || (!relation.getIdUserFrom().equals(initiator.getIdUser()) && !relation.getIdUserTo().equals(initiator.getIdUser())))
            throw new GenericException("Not allowed !");
        
        relationRepository.deleteById(relationID);
    }
    
    private void validateRelation(User initiator, Relation relation) throws Exception {
        if(initiator == null
                || relation.getIdUserFrom() == null
                || relation.getIdUserTo() == null
                || (!relation.getIdUserFrom().equals(initiator.getIdUser()) && !relation.getIdUserTo().equals(initiator.getIdUser())))
            throw new GenericException("Not allowed !");
        
        if(relation.getParamRelationType() == null)
            throw new GenericException("Type relation can't be null !");
    }
}
