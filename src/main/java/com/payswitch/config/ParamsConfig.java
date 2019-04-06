/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payswitch.config;

/**
 *
 * @author Dell
 */
public class ParamsConfig {

    //*********************************** Security Config Params ***********************//
    public static final String TRUSTED_CLIENT_ID = "my-trusted-client";
    public static final String TRUSTED_CLIENT_SECRET = "secret";
    public static final String TOKEN_GENERATION_URL = "/oauth/token";
    public static final Integer ACCESS_TOKEN_VALDITION_TIME = 60 * 60 * 10;
    public static final String SECURED_RESOURCE_URL = "/api/**";
    public static final String[] OAUTH_SECURITY_SCOPE = {"read", "write", "trust"};
    public static final String[] OAUTH_SECURITY_AUTHORITIES = {"ROLE_CLIENT", "ROLE_TRUSTED_CLIENT"};
    public static final String OAUTH_SECURITY_RESOURCE_ID = "oauth2-resource";
    public static final String[] OAUTH_AUTHORIZATION_GRANT_TYPE = {"client_credentials", "password"};
    public static final String API_DATA_FORMAT = "application/json";

    //****************************Config Params ********************//
    public static final Integer ADAPTER_CORE_POOLING_SIZE = 2;
    public static final Integer ADAPTER_MAX_POOLING_SIZE = 2;
    public static final Integer ADAPTER_QUEUE_CAPACITY = 1000;
    public static final String ADAPTER_DEFAULT_PREFIX = "PAYMENT-SWITCH->";

//****************************** THREAD CONFIG *************************************//
    public static final Integer NUMBER_OF_THREAD = 4;
    public static final Integer MESSAGE_HEADER_LENGTH = 0;

}
