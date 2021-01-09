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
@Table(name = "relation")
public class Relation implements Serializable {
    
    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRelation;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "param_relation_type")
    private Parameter paramRelationType;
    
    @Column
    private Integer idUserFrom;
    
    @Column
    private Integer idUserTo;
}
