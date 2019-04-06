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
public class SVMessageUtil {
    
    /**
     * Get phone number by providing accountNumber
     * @param primaryAccountNumber
     * @return 
     */
    public String getPhoneNumber(String accountNumber)
    {
        String phoneWithoutZero = accountNumber.substring(7);
        return "0"+phoneWithoutZero;
    }
    
    public String getTransactionType(String accountNumber)
    {
        String type = "C2C";
        String tnxTypeFlag = accountNumber.substring(6,7);
        
        switch(tnxTypeFlag){
            
            case "1":
                type = "C2A";
                break;
            case "2":
                type = "A2C";
                break;
            case "3":
                type = "A2A";
                break;        
        }
        
        return type;
    }
    
}
