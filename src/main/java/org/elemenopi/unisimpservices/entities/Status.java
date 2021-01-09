/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * @author Elemenopi
 */
@Data
@MappedSuperclass
public abstract class Status {

    @Column(nullable = false)
    protected Boolean isDeleted = false;
}


