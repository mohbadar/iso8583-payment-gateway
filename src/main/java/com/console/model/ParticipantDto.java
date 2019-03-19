/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Dell
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ParticipantDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String status;
    private String tazkraSerialNumber;
    private Integer tazkraPageNumber;
    private String tazkraJuld;
    private String phone;
    private String email;
    private String address;
    private String province;
    private String district;
    private String birthPlace;
    private String birthYear;
    private String photo;
    private String eductaion;
    private boolean isEntered = false;
    private boolean isAllowed;

}
