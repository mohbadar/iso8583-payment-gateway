/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.iso.util;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dell
 */
public class IsoMessageUtil {

    //logger
    private static final Logger log = LoggerFactory.getLogger(IsoMessageUtil.class);

    /**
     * Utility method to safely get a {@code String} value from a field in the
     * {@linkplain IsoMessage}
     */
    public static String getStringValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validStringValue(isoMessage.getField(field))) {
            return null;
        }
        return (String) isoMessage.getField(field).getValue();
    }

    private static boolean validStringValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.ALPHA || type == IsoType.LLLVAR || type == IsoType.LLLVAR);
    }

    /**
     * Utility method to safely get a {@code BigDecimal} value from a field in
     * the {@linkplain IsoMessage}
     */
    public static BigDecimal getBigDecimalValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validNumericValue(isoMessage.getField(field))) {
            return null;
        }
        return (BigDecimal) isoMessage.getField(field).getValue();
    }

    private static boolean validNumericValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.AMOUNT || type == IsoType.NUMERIC);
    }

    /**
     * Utility method to safely get a int value from a field in the
     * {@linkplain IsoMessage}
     */
    public static int getNumericValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validNumericValue(isoMessage.getField(field))) {
            return -1;
        }
        String number = (String) isoMessage.getField(field).getValue();
        return number != null ? Integer.parseInt(number) : -1;
    }

    /**
     * Utility method to safely get a {@code Date} value from a field in the
     * {@linkplain IsoMessage}
     */
    public static Date getDateValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validDateValue(isoMessage.getField(field))) {
            return null;
        }
        return (Date) isoMessage.getField(field).getValue();
    }

    /**
     * Private method to validate if field has a valid {@code Date}
     * {@code IsoType}
     */
    private static boolean validDateValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.DATE_EXP || type == IsoType.DATE4 || type == IsoType.DATE10);
    }

    /**
     * Utility method to safely set a {@code String} value to a
     * {@linkplain IsoMessage} using the corresponding {@linkplain IsoType}
     */
    public static void setStringValue(IsoMessage isoMessage, String value, int field, int positions) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.ALPHA, positions);
        }
    }

    /**
     * Utility method to safely set a {@code BigDecimal} value to a
     * {@linkplain IsoMessage} using the corresponding {@linkplain IsoType}
     */
    public static void setBigDecimalValue(IsoMessage isoMessage, BigDecimal value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.AMOUNT, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date} value to a
     * {@linkplain IsoMessage} as a TIME {@linkplain IsoType}
     */
    public static void setTimeValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.TIME, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date} value to a
     * {@linkplain IsoMessage} as a DATE10 {@linkplain IsoType}
     */
    public static void setDateValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE10, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date} value to a
     * {@linkplain IsoMessage} as a DATE4 {@linkplain IsoType}
     */
    public static void setDate4Value(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE4, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date} value to a
     * {@linkplain IsoMessage} as a DATE_EXP {@linkplain IsoType}
     */
    public static void setDateExpValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE_EXP, 0);
        }
    }

    /**
     * Utility method to safely set a variable alphanumeric value to a
     * {@linkplain IsoMessage} using the corresponding {@linkplain IsoType}
     * depending on the variable length
     */
    public static void setVarLengthValue(IsoMessage isoMessage, String value, int field, int variableLength) {
        IsoType isoType = null;
        if (value != null && (variableLength == 2 || variableLength == 3)) {
            if (variableLength == 2) {
                isoType = IsoType.LLVAR;
            } else if (variableLength == 3) {
                isoType = IsoType.LLLVAR;
            }
            isoMessage.setValue(field, value, isoType, 0);
        }
    }

    /**
     * Utility method to safely set a {@code int} value to a
     * {@linkplain IsoMessage} using the corresponding {@linkplain IsoType}
     */
    public static void setNumericValue(IsoMessage isoMessage, int value, int field, int length) {
        if (value != -1) {
            isoMessage.setValue(field, String.valueOf(value), IsoType.NUMERIC, length);
        }
    }

    /**
     * Utility method to safely set a {@code String} value to a
     * {@linkplain IsoMessage} using the corresponding {@linkplain IsoType}
     */
    public static void setNumericValue(IsoMessage isoMessage, String value, int field, int length) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.NUMERIC, length);
        }
    }

    /**
     * Utility to correctly format a {@code Date} as a {@linkplain IsoType}
     * DATE10 field.
     */
    public static String formatDate10(Date date) {
        IsoType isoType = IsoType.DATE10;
        return isoType.format(date, TimeZone.getDefault());
    }

    /**
     * Utility to correctly format a {@code Date} as a {@linkplain IsoType}
     * DATE4 field.
     */
    public static String formatDate4(Date date) {
        IsoType isoType = IsoType.DATE4;
        return isoType.format(date, TimeZone.getDefault());
    }

    /**
     * Utility to correctly format a {@code Date} as a {@linkplain IsoType}
     * DateExp field.
     *
     */
    public static String formatDateExp(Date date) {
        IsoType isoType = IsoType.DATE_EXP;
        return isoType.format(date, TimeZone.getDefault());
    }

    /**
     * Utility to correctly format a {@code Date} as a {@linkplain IsoType} TIME
     * field.
     */
    public static String formatTime(Date date) {
        IsoType isoType = IsoType.TIME;
        return isoType.format(date, TimeZone.getDefault());
    }

    /**
     * Utility to correctly format a {@code BigDecimal} as a
     * {@linkplain IsoType} Amount field.
     *
     */
    public static String formatAmount(BigDecimal value, int length) {
        IsoType isoType = IsoType.AMOUNT;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code long} as a {@linkplain IsoType}
     * NUMERIC field.
     */
    public static String formatNumeric(long value, int length) {
        IsoType isoType = IsoType.NUMERIC;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code String} as a {@linkplain IsoType}
     * NUMERIC field.
     */
    public static String formatNumeric(String value, int length) {
        IsoType isoType = IsoType.NUMERIC;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code String} as a {@linkplain IsoType}
     * ALPHA field.
     */
    public static String formatAlphaNumeric(String value, int length) {
        IsoType isoType = IsoType.ALPHA;
        return isoType.format(value, length);
    }

    public static Integer formatDate6(String number) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddhhmmss");
        Date date = new Date();

        try {
            date = dateFormat.parse(number);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        SimpleDateFormat convertedDate = new SimpleDateFormat("yyMMdd");
        return Integer.parseInt(convertedDate.format(date));
    }

    public static void printIsoMessage(IsoMessage m) {
        String[] logs = new String[128];
        System.out.printf("TYPE: %04x\n", m.getType());
        for (int i = 2; i < 128; i++) {
            if (m.hasField(i)) {
                System.out.printf("F %3d(%s): %s -> '%s'\n", i, m.getField(i)
                        .getType(), m.getObjectValue(i), m.getField(i)
                        .toString());
//                log.debug(String.format("DF %3d(%s): %s -> '%s'\n",  i, m.getField(i)
//                        .getType(), m.getObjectValue(i), m.getField(i)
//                        .toString()));
            logs[i] = String.format("DF %3d(%s): %s -> '%s'\n",  i, m.getField(i)
                        .getType(), m.getObjectValue(i), m.getField(i)
                        .toString());
            }
        }
        log.debug("ISO MESSAGE: ", logs.toString());
    }
}
