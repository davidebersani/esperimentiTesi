package com.example.springcloudgateway.domain;

public class MalformedStringOfOperationsException extends Exception {

    public MalformedStringOfOperationsException(String errorMessage){
        super(errorMessage);
    }
}
