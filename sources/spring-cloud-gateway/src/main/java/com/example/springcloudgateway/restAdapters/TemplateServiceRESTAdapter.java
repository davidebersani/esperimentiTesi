package com.example.springcloudgateway.restAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springcloudgateway.service.TemplateServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
@Configuration
@Slf4j
public class TemplateServiceRESTAdapter implements TemplateServicePort {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void makeRESTcallToService(String serviceToCall, String payload) {

        ServiceInstance instance = loadBalancer.choose(serviceToCall);

        if(instance==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "istanza del servizio " + serviceToCall + " non trovata");

        StringBuilder sb = new StringBuilder();
        sb.append(instance.getUri().toString());
        sb.append("/prosegui");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            log.debug("Eseguo chiamata POST al servizio " + serviceToCall);
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(sb.toString(), request, String.class);
            log.debug("Risposta chiamata: " + responseEntityStr);
        }catch (HttpServerErrorException.InternalServerError e) {
            log.debug("errore del server con msg: " + getMessageOfHttpErrorException(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getMessageOfHttpErrorException(e));
        }catch (HttpClientErrorException.BadRequest e) {
            log.debug("errore del client con msg: " + getMessageOfHttpErrorException(e));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getMessageOfHttpErrorException(e));
        }

    }

    @Override
    public void makeSafeRESTcallToService(String serviceToCall, String payload) {
        try {
            this.makeRESTcallToService(serviceToCall, payload);
        }catch (ResponseStatusException e) {
            log.debug("Essendo una safecall non propago l'eccezione http: " + e.toString());
        }
    }

    private String getMessageOfHttpErrorException(HttpStatusCodeException e) {

        ObjectMapper mapper = new ObjectMapper();
        String messageOfHttpErrorException;

        try {
            messageOfHttpErrorException = mapper.readValue(e.getResponseBodyAsString(), CustomHttpErrorException.class).getMessage();
        } catch (IOException ex) {
            log.debug("IOException: " + ex.toString());
            messageOfHttpErrorException = "no message retrieved";
        }

        return messageOfHttpErrorException;
    }
}
