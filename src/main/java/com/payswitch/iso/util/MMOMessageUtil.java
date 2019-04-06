/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.iso.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class MMOMessageUtil {

    public String getPointOfServiceCode() {
        return "060000065001";
    }

    public String getCardSequenceNumber() {
        return "11";
    }

    public Integer getAcquiringInstitutionIdentificationCode(String phoneNumber) {
        String phonePrefix = phoneNumber.substring(1, 3);
        return getSVBin(phonePrefix);
    }

    public String getPAN(String phoneNumber, String txType) {
        int binPrefex = getBinPrefex(txType);
        String phone = phoneNumber.substring(1);
        Integer sourceMMOBIN = Constants.SOURCE_MMO_BIN;
        String PAN = String.format("%s%s%s", sourceMMOBIN, binPrefex, phone);
        return PAN;
    }

    /**
     * get the DestinationWalletPAN based on phone number
     *
     * @param phoneNumber
     * @return DestinationWalletPAN
     */
    public String getPANExtended(String phoneNumber, String txType) {
        int binPrefex = getBinPrefex(txType);
        String phone = phoneNumber.substring(1);
        String phonePrefix = phoneNumber.substring(1, 3);
        Integer SVBIN = getSVBin(phonePrefix);
        String PANExtended = String.format("%s%s%s", SVBIN, binPrefex, phone);

        return PANExtended;
    }

    /**
     *
     * @param phonePrefix
     * @return sv bin
     */
    private Integer getSVBin(String phonePrefix) {
        if (phonePrefix.equals("79") || phonePrefix.equals("72")) {
            //m-paisa 
            return Constants.MPAISA_BIN;
        } else if (phonePrefix.equals("70") || phonePrefix.equals("71")) {
            //awcc
            return Constants.MY_MONEY_BIN;
        } else if (phonePrefix.equals("78")) {
            //m hawala
            return Constants.MHAWALA_BIN;
        } else if (phonePrefix.equals("77")) {
            //mtn
            return Constants.MTN_BIN;
        }

        return 0000;
    }

    /**
     * 0 --> C2C, 1 --> C2A, 2 --> A2C, 3 --> A2A
     */
    private Integer getBinPrefex(String txType) {

        Integer binPrefex = 0;
        switch (txType) {
            case Constants.C2A:
                binPrefex = 1;
                break;

            case Constants.A2C:
                binPrefex = 2;
                break;

            case Constants.A2A:
                binPrefex = 3;
                break;

            default:
                break;
        }
        return binPrefex;
    }

    public String getCardAcceptorTerminalIdentificationCode(String primaryAccountNumberExtended) {
        return primaryAccountNumberExtended.substring(2);
    }

    public String getCardAcceptorIdentificationCode(String primaryAccountNumberExtended) {
        return primaryAccountNumberExtended;
    }

    public Integer getCurrencyCode() {
        return 971;
    }

    /**
     * Return transmission date and time
     *
     * @param localTransactionDateAndTime
     * @return
     */
    public String getTransmissionDateAndTime(String localTransactionDateAndTime) {
        Date date = getDate(localTransactionDateAndTime);
        return IsoMessageUtil.formatDate10(date);
    }

    /**
     * Return date object by receiving the localTransactionDateAndTime
     *
     * @param localTransactionDateAndTime
     * @return Date
     */
    private Date getDate(String localTransactionDateAndTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddhhmmss");
        Date date = new Date();

        try {
            date = dateFormat.parse(localTransactionDateAndTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return date;
    }

    /**
     * Returns
     *
     * @param localTransactionDateAndTime
     * @return Expiration Date
     */
    public String getExpirationDate(String localTransactionDateAndTime) {
        Date date = getDate(localTransactionDateAndTime);
        return IsoMessageUtil.formatDateExp(date);
    }

    public String getProccessingCode(String ft) {

        String processingCode = "311000";

//        switch (ft) {
//            case "FT":
//                processingCode = "";
//                break;
//            case "Purchase":
//                processingCode = "";
//                break;
//            case "CW":
//                processingCode = "";
//                break;
//            case "Lookup":
//                processingCode = "";
//                break;
//            case "ParameterInquiry":
//                processingCode = "";
//                break;
//
//        }
        return processingCode;
    }

    public String getSystemTraceNumber(String retreivalReferenceNumber) {
        return retreivalReferenceNumber.substring(0, 5);
    }

    public String getSettlement(String localTransactionDateAndTime) {
        return IsoMessageUtil.formatDate6(localTransactionDateAndTime) + "";
    }

    public String getCardHolderNameAndLocation(String primaryAccountNumber) {
        return Constants.SOURCE_MMO_BIN + ">" + primaryAccountNumber;
    }

    public String getMerchantType() {
        return "9401";
    }

    public String getCardHolderBillingAmount(Long transactionAmount) {
        return ""+transactionAmount;
    }

    public String getCardHolderBillingConversionRate() {
        return "0";
    }

}
