package com.example.springcloudgateway.service;

public interface TemplateServicePort {

    void makeRESTcallToService(String serviceToCall, String payload);

}
