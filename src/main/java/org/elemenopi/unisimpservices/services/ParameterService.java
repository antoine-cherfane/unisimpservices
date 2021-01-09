/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.elemenopi.unisimpservices.entities.ListParameter;
import org.elemenopi.unisimpservices.entities.Parameter;
import org.elemenopi.unisimpservices.repositories.ListParameterRepository;
import org.elemenopi.unisimpservices.repositories.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class ParameterService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    ParameterRepository parameterRepository;
    
    @Autowired
    ListParameterRepository listParameterRepository;
    
    public ObjectNode getMultipleListParameter(String body) throws Exception {
        ObjectNode response = mapper.createObjectNode();
        List<String> allListParameterName = mapper.readValue(body, List.class);
        for(String name : allListParameterName) {
            response.set(name, getAllParameterByListParameterName(name));
        }
        return response;
    }
    
    public ArrayNode getAllParameterByListParameterName(String name) throws Exception {
        Optional<ListParameter> optionalListParameter = listParameterRepository.findByName(name);
        List<Parameter> allParameter = new ArrayList<>();
        if(optionalListParameter.isPresent()) 
            allParameter = parameterRepository.findByListParameterID(optionalListParameter.get().getIdListParameter());
        return mapper.convertValue(allParameter, ArrayNode.class);
    }
    
    public Parameter createParameter(Integer parameterID) {
        Parameter parameter = new Parameter();
        parameter.setIdParameter(parameterID);
        return parameter;
    }
}
