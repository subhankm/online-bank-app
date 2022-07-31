package com.subhan.onlinebank.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;
    private String dateTime;
    
}