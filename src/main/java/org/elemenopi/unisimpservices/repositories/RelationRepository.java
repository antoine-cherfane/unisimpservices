/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import java.util.List;
import org.elemenopi.unisimpservices.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
        
/**
 *
 * @author Elemenopi
 */
public interface RelationRepository extends JpaRepository<Relation,Integer> {
    @Query("SELECT relation FROM Relation relation WHERE relation.idUserFrom = :#{#userID} or relation.idUserTo = :#{#userID}")
    List<Relation> findByUserID(@Param("userID") Integer userID);
    
    @Query("SELECT relation FROM Relation relation WHERE (relation.idUserFrom = :#{#userID} or relation.idUserTo = :#{#userID}) and relation.paramRelationType.idParameter = :#{#relationTypeID}")
    List<Relation> findByUserIDAndRelationTypeID(@Param("userID") Integer userID, @Param("relationTypeID") Integer relationTypeID);
    
    
}



