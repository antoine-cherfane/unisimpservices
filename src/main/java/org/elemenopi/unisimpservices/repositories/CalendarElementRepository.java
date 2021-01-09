/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import org.elemenopi.unisimpservices.entities.CalendarElement;
import org.springframework.data.jpa.repository.JpaRepository;
        
/**
 *
 * @author Elemenopi
 */
public interface CalendarElementRepository extends JpaRepository<CalendarElement,Integer> {
}



