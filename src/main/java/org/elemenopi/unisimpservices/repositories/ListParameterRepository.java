/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import java.util.Optional;
import org.elemenopi.unisimpservices.entities.ListParameter;
import org.springframework.data.jpa.repository.JpaRepository;
        
/**
 *
 * @author Elemenopi
 */
public interface ListParameterRepository extends JpaRepository<ListParameter,Integer> {
    Optional<ListParameter> findByName(String name);
}



