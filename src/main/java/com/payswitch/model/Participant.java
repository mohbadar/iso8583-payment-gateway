/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author Dell
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Data
public class Participant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 64, name = "first_name", nullable = false)
    private String firstName;
    @Column(length = 64, name = "last_name", nullable = true)
    private String lastName;
    @Column(length = 64, name = "gender", nullable = false)
    private Boolean gender;
    @Column(length = 10, name = "status", nullable = false)
    private String status;
    @Column(length = 64, name = "tazkra_serial_number", nullable = false)
    private String tazkraSerialNumber;
    @Column(length = 64, name = "tazkra_page_number", nullable = false)
    private Integer tazkraPageNumber;
    @Column(length = 32, name = "tazkra_juld", nullable = false)
    private String tazkraJuld;
    @Column(length = 13, name = "phone", nullable = false)
    private String phone;
    @Column(length = 128, name = "email", nullable = true)
    private String email;
    @Column(length = 255, name = "address", nullable = false)
    private String address;
    @Column(length = 32, name = "province", nullable = false)
    private String province;
    @Column(length = 32, name = "district", nullable = false)
    private String district;
    @Column(length = 255, name = "birth_place", nullable = false)
    private String birthPlace;
    @Column(length = 4, name = "birth_year", nullable = false)
    private String birthYear;
    @Column(length = 64, name = "photo_url", nullable = false)
    private String photo;
    @Column(length = 255, name = "tazkra_url", nullable = true)
    private String tazkra;
    @Column(length = 255, name = "passport_url", nullable = true)
    private String passport;
    @Column(length = 64, name = "education", nullable = false)
    private String eductaion;
    @Column(name = "is_entered")
    private boolean isEntered = false;
    @Column(name = "is_allowed")
    private boolean isAllowed;

}
