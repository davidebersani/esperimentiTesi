package esperimenti.stubservice.msgAdapters;

import esperimenti.stubservice.operationsParsers.OperationsStringParser;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListenerAdapter {

    @Autowired
    private OperationsStringParser operationsStringParser;

    @Timed(value="template.msg", description = "timer per processamento messaggi", extraTags = {"operation" , "listen"})
    @KafkaListener(topics = "${template.kafka.channel.in}", groupId="${template.kafka.groupid}")
    public void listen(ConsumerRecord<String, String> record) {

        String receivedMessage = record.value();
        log.debug("messaggio ricevuto: " + receivedMessage);

        try {
            operationsStringParser.parseOperations(receivedMessage);
        } catch (Exception e ) {
            e.printStackTrace(); //TODO: gestire eccezione (cosa fare?)
        }

    }
}
