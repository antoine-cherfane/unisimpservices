/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elemenopi.unisimpservices.entities;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

/**
 *
 * @author Elemenopi
 */
@Data
@Entity
@Table(name = "user")
@Where(clause = "is_deleted = false")
public class User extends Status implements Serializable {

    @Id
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    
    @Column
    private String username;
    
    @Column
    private String mail;
    
    @Column
    private String password;
    
    @Column
    private String picturePath;
    
    @Column
    private String createdAt;
    
    @Column(nullable = false)
    private Boolean isPrivate = false;
    
    @Column
    private Integer notifyBefore = 10;
    
    @Column
    private Boolean sendEmailNotifications = false;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "obj_calendar")
    private Calendar objCalendar;
}
