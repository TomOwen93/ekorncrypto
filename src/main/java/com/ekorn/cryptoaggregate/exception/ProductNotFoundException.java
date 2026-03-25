package com.ekorn.cryptoaggregate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ProductNotFoundException extends HttpStatusCodeException {
    public ProductNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
