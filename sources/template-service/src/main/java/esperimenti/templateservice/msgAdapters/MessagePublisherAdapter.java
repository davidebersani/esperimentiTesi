package esperimenti.templateservice.msgAdapters;

import esperimenti.templateservice.messages.CommandMessage;
import esperimenti.templateservice.service.MessagePublisherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisherAdapter implements MessagePublisherPort {

    @Autowired
    private KafkaTemplate<String, CommandMessage> template;

    @Override
    public void publish(CommandMessage commandMessage, String service) {
        template.send(service,commandMessage);
    }
}
