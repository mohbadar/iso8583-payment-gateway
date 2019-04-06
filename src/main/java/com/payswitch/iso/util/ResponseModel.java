package com.payswitch.iso.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
@ToString
public class ResponseModel<T> {

    private T data;

    private int statusCode;

    private ErrorResponseModel error;
}
