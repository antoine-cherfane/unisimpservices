/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.repositories;

import java.util.List;
import org.elemenopi.unisimpservices.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Elemenopi
 */
public interface ParameterRepository extends JpaRepository<Parameter,Integer> {
    @Query("SELECT parameter FROM Parameter parameter WHERE parameter.objListParameter.idListParameter = :#{#listParameterID}")
    List<Parameter> findByListParameterID(@Param("listParameterID") Integer listParameterID);
}
