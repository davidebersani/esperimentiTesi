package esperimenti.templateservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TemplateServicePort {

    void proseguiVersoServizio(String message, String service) throws JsonProcessingException;

}
