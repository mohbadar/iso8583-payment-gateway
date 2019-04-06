/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.iso.util;

/**
 *
 * @author Dell
 */
public class IsoMessageType {

    public static final Integer AUTHORIZATION_REQUEST = 0X1100;
    public static final Integer AUTHORIZATION_RESPONSE = 0X1100;
    public static final Integer ADIVCE_MESSAGE_REQUEST = 0X1120;
    public static final Integer ADVICE_MESSAGE_RESPONSE = 0X1130;
    public static final Integer ADVICE_REPEAT_REQUEST = 0X1121;
    public static final Integer REVERSAL_REQUEST = 0X1420;
    public static final Integer REVERSAL_REPEAT_REQUEST = 0X1421;
    public static final Integer REVERSAL_RESPONSE = 0X1430;
    public static final Integer ADMINISTRATIVE_REQUEST = 0X1600;
    public static final Integer ADMINISTRATIVE_RESPONSE = 0X1610;
    public static final Integer NETWORK_MANAGEMENT_REQUEST = 0X1804;
    public static final Integer NETWORK_MANAGEMENT_RESPONSE = 0X1814;
    public static final Integer PAYMENT_PARAMETERS_INQUIRY_REQUEST = 0X1100;
    public static final Integer PAYMENT_PARAMETERS_INQUIRY_RESPONSE = 0X1100;
    public static final Integer PAYMENT_REQUEST = 0x1200;
    public static final Integer PAYMENT_RESPONSE =0x12100;
    public static final Integer LOOKUP_REQUEST = 0x1100;
    public static final Integer LOOKUP_RESPONSE  = 0x1110;

}
