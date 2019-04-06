package com.payswitch.iso.util;

import af.aps.mno.enums.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;



@Data
@AllArgsConstructor
public class ErrorResponseModel {

    private ExceptionType exceptionType;

    private String errorCode;

    private String errorMessage;

    private long time;

    private List<String> errorCause;
}
