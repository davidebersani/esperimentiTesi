package esperimenti.templateservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import esperimenti.templateservice.domain.CallPOJO;
import esperimenti.templateservice.domain.IPCType;
import esperimenti.templateservice.messages.CommandMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    TemplateServicePort templateServicePort;

    @Autowired
    private MessagePublisherPort publisher;

    public void prosegui(List<CallPOJO> calls) throws JsonProcessingException{

        for(CallPOJO call:calls) {

            if (call.getIpc_type() == IPCType.MESSAGE) {

                CommandMessage cm = new CommandMessage(call.isGoing_to_fail(), call.getNext_calls());
                publisher.publish(cm, call.getService_to_call());

            }else if (call.getIpc_type() == IPCType.REST) {

                if (call.isGoing_to_fail()) {

                    templateServicePort.proseguiVersoServizoCheFallisce(call);

                }else {

                    templateServicePort.proseguiVersoServizio(call);

                }
            }

        }
    }

    public void errore(List<CallPOJO> calls) throws Exception {
        log.info("è stato richiamato l'endpoint /errore che restituisce 500");
        throw new Exception();
    }

}