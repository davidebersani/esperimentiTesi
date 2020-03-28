package esperimenti.templateservice.msgAdapters;

import esperimenti.templateservice.messages.CommandMessage;
import esperimenti.templateservice.service.MessagePublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
//@EnableAutoConfiguration
@Slf4j
public class MessagePublisherAdapter implements MessagePublisherPort {

    @Autowired
    private KafkaTemplate<String, CommandMessage> template;

    @Override
    public void publish(CommandMessage commandMessage, String service) {
        log.info("invio al servizio: " + service + " il messaggio: " + commandMessage.toString());
        template.send(service, commandMessage);
    }
}
