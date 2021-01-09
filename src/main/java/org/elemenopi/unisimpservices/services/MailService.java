/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class MailService {
    
    @Autowired
    JavaMailSender emailSender;
    
    @Value("${spring.mail.host}")
    String mailFrom;
    
    public Boolean isValidEmail(String mail) {
        if(mail == null || mail.isBlank()) return false;
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mail); 
        return matcher.matches();
    }
 
    public void sendMailMessage(String mailTo, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(mailFrom);
        message.setTo(mailTo); 
        message.setSubject(subject); 
        message.setText(body);
        emailSender.send(message);
    }
}
