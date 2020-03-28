package esperimenti.templateservice.msgAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import esperimenti.templateservice.messages.CommandMessage;
import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListenerAdapter {

    @Value("${template.kafka.channel.in}")
    private String channel;

    @Value("${template.kafka.groupid}")
    private String groupId;

    @Autowired
    private TemplateService templateService;

    @KafkaListener(topics = "${template.kafka.channel.in}", groupId="${template.kafka.groupid}")
    public void listen(ConsumerRecord<String, CommandMessage> record) throws JsonProcessingException {

        CommandMessage cm = record.value();

        //log.info("record ricevuto: " + record.toString());
        log.info("messaggio ricevuto: " + cm);

        if(cm.isGoingToFail()) {
            try {
                templateService.errore(cm.getCalls());
            } catch (Exception e)
            {
                // TODO: gestire l'eccezione
            }
        } else {
            templateService.prosegui(cm.getCalls());
        }
    }
}
