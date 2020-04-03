package esperimenti.templateservice.restAdapters;

import esperimenti.templateservice.service.TemplateServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class TemplateServiceRESTAdapter implements TemplateServicePort {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public void makeRESTcallToService(String serviceToCall, String payload) {

        ServiceInstance instance = loadBalancer.choose(serviceToCall);

        if(instance!=null) {

            StringBuilder sb = new StringBuilder();
            sb.append(instance.getUri().toString());
            sb.append("/prosegui");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<String> request = new HttpEntity<String>(payload, headers);

            //log.info("Eseguo chiamata al servizio " + serviceToCall);

            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(sb.toString(), request, String.class);
                //log.info("Risposta chiamata: " + responseEntityStr);
            }catch (HttpServerErrorException e) {
                log.info("responsebody: " + e.getResponseBodyAsString());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        }else {
            //log.info("servizio " + serviceToCall + " non trovato");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "istanza del servizio " + serviceToCall + " non trovata");
        }
    }
}
