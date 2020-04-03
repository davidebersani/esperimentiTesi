package esperimenti.templateservice.msgAdapters;

import esperimenti.templateservice.service.MessagePublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
//@EnableAutoConfiguration
@Slf4j
public class MessagePublisherAdapter implements MessagePublisherPort {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Override
    public void publish(String message, String service) {
        log.info("invio al servizio: " + service + " il messaggio: " + message);
        template.send(service, message);
    }
}
