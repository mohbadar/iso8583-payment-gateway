/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.iso.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dell
 */
@Slf4j
public class Utility {
    

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static TypeFactory typeFactory = objectMapper.getTypeFactory();

    public static boolean isNullOrBlank(String key) {
        int strLen;
        if (key != null && (strLen = key.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(key.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String objectToJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonToString = mapper.writeValueAsString(object);
            return jsonToString;
        } catch (Exception var3) {
            log.error("exception", var3);
            return null;
        }
    }

    public static <T1> List<T1> convertModelList(List<? extends Object> sourceClass, Class<T1> destinationClass) {
        try {
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, destinationClass);
            return objectMapper.convertValue(sourceClass, collectionType);
        } catch (Exception exp) {
            log.error("[convertModelList] Exception occurred while converting model list", exp);
            return null;
        }
    }

    public static <T> T convertModel(Object sourceClass, Class<T> destinationClass) {
        try {
//            JavaType javaType = typeFactory.constructType(destinationClass);
            return objectMapper.convertValue(sourceClass, destinationClass);
        } catch (Exception exp) {
            log.error("Exception", exp);
            return null;
        }
    }

    public static Long getLongValue(String str) {
        try {
            return new Long(str);
        } catch (Exception e) {
            log.error("Error Occurred while converting String to Long value - " + str, e);
        }
        return 0L;
    }

    public static boolean getBooleanValue(String str) {
        try {
            return Boolean.valueOf(str);
        } catch (Exception e) {
            log.error("Error occurred while converting String to boolean - " + str, e);
        }
        return false;
    }

    public static boolean isNonEmpty(Collection<?> collection) {
        return (collection != null && !collection.isEmpty());
    }

}
