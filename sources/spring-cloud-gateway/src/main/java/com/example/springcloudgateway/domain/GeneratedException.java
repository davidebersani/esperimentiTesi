package com.example.springcloudgateway.domain;

public class GeneratedException extends Exception {

    public GeneratedException(String errorMessage) {
        super(errorMessage);
    }
}
