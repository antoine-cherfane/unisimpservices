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
@Table(name = "parameter")
public class Parameter implements Serializable {

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParameter;

    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name = "obj_list_parameter")
    private ListParameter objListParameter;
}
