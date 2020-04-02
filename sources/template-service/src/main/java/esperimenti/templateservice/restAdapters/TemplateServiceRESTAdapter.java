package esperimenti.templateservice.restAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import esperimenti.templateservice.domain.CallPOJO;
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
    public void proseguiVersoServizio(String message, String service) throws JsonProcessingException {
        eseguiChiamata(message,service);
    }

    private void eseguiChiamata(String message, String service) throws JsonProcessingException {

        ServiceInstance instance = loadBalancer.choose(service);

        if(instance!=null) {

            StringBuilder sb = new StringBuilder();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            ObjectMapper objectMapper = new ObjectMapper();

            sb.append(instance.getUri().toString());

            sb.append("/prosegui");

            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = message;
            log.info("Body convertito in json: " + body);

            HttpEntity<String> request = new HttpEntity<String>(body, headers);

            log.info("Eseguo chiamata al servizio " + service);

            try {
                ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(sb.toString(), request, String.class);
                log.info("Risposta chiamata: " + responseEntityStr);
            } catch (HttpServerErrorException e) {
                //log.info("\nresponsebody: " + e.getResponseBodyAsString());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "si è verificato un errore nell'istanza chiamata");
            }

        }else {
            // TODO: Sollevare eccezione
        }
    }

}
