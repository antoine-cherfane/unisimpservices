/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import java.util.Optional;
import java.util.List;
import org.elemenopi.unisimpservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Elemenopi
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    
    List<User> findBySendEmailNotifications(Boolean sendEmailNotifications);
}
