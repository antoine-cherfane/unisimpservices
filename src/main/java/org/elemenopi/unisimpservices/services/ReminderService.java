/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.elemenopi.unisimpservices.entities.CalendarElement;
import org.elemenopi.unisimpservices.entities.User;
import org.elemenopi.unisimpservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elemenopi
 */
@Service
public class ReminderService {
    
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    MailService mailService;
    
    @Autowired
    UserRepository userRepository;
    
    @Scheduled(fixedRate = 60000) // in ms
    private void handleReminders() throws Exception {
        List<User> allUsers = userRepository.findBySendEmailNotifications(true);
        
        Date relatedMonday = getFirstDayOfWeek();
        Date cleanDateNow = getCleanDateNow();
        for(User user : allUsers) {
            if(!mailService.isValidEmail(user.getMail())
                    || !user.getSendEmailNotifications()
                    || user.getNotifyBefore() == null
                    || user.getObjCalendar() == null
                    || user.getObjCalendar().getSetCalendarElement() == null)
                continue;
            
            for(CalendarElement calendarElement : user.getObjCalendar().getSetCalendarElement()) {
                if(calendarElement.getFromTime() == null || calendarElement.getDayNum() == null || calendarElement.getTitle() == null)
                    continue;
                
                String fromTime = calendarElement.getFromTime();
                String[] allParts = fromTime.split(":");
                
                if(allParts.length != 2)
                    continue;
                
                Integer hours = Integer.parseInt(allParts[0]);
                Integer minutes = Integer.parseInt(allParts[1]);
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(relatedMonday);
                calendar.add(Calendar.DATE, calendarElement.getDayNum() - 1);
                calendar.set(Calendar.HOUR_OF_DAY, hours);
                calendar.set(Calendar.MINUTE, minutes);
                
                calendar.add(Calendar.MINUTE, -1 * user.getNotifyBefore());
                
                if(cleanDateNow.equals(calendar.getTime())) {
                    String body = "Votre cours de " + calendarElement.getTitle() + " va commencer dans " + user.getNotifyBefore() + " minutes !";
                    mailService.sendMailMessage(user.getMail(), "UniSimp Reminder", body);
                }
            }
        }
    }
    
    private Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        
        boolean isSunday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        if(isSunday)
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        

        calendar.add(Calendar.DATE, 1);
        
        return calendar.getTime();
    }
    
    private Date getCleanDateNow() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        
        return calendar.getTime();
    }
}
