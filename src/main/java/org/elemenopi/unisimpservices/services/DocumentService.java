/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Elemenopi
 */
@Service
public class DocumentService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Value("${media-absolute-path}")
    String mediaAbsolutePath;
    
    public ObjectNode upload(MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        String path = "uploads/" + System.currentTimeMillis() + "-" + name;
        
        File uploaded = new File(mediaAbsolutePath + path);
        uploaded.createNewFile();
        
        FileOutputStream out = new FileOutputStream(uploaded);
        out.write(file.getBytes());
        out.close();
        
        ObjectNode response = mapper.createObjectNode();
        response.set("path", mapper.convertValue(path, JsonNode.class));
        
        return response;
    }
}
