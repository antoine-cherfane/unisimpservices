/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import java.util.List;
import org.elemenopi.unisimpservices.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
        
/**
 *
 * @author Elemenopi
 */
public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT message FROM Message message WHERE message.idUserFrom = :#{#userID} or message.idUserTo = :#{#userID}")
    List<Message> findByUserID(@Param("userID") Integer userID);
}



