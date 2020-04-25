package com.example.springcloudgateway.domain;

public class ConcurrentExecutionException extends Exception {

    public ConcurrentExecutionException(String errorMessage, Exception cause) {
        super(errorMessage + " - " + cause.toString());
    }
}
