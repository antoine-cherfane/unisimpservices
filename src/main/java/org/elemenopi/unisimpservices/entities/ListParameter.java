/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author Elemenopi
 */
@Data
@Entity
@Table(name = "list_parameter")
public class ListParameter implements Serializable {
    
    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idListParameter;

    @Column
    private String name;
    
}
