package esperimenti.templateservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import esperimenti.templateservice.domain.CallPOJO;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface TemplateServicePort {

    void proseguiVersoServizio(String message, String service) throws JsonProcessingException;

}
