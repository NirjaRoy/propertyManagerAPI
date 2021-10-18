package com.api.propertyManager.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
public class PropertyNotFoundException extends RuntimeException{
    private HttpStatus status;
    private String message;
    private String error;

}
