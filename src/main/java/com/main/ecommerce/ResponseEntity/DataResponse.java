package com.main.ecommerce.ResponseEntity;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class DataResponse {

    private HttpStatus status;
    private String message;
    private Object data;

}
