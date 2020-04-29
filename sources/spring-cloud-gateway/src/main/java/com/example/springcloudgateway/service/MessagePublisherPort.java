package com.example.springcloudgateway.service;

public interface MessagePublisherPort {

    void notify(String serviceToNotify, String payload);

}
