package esperimenti.stubservice.msgAdapters;

import esperimenti.stubservice.service.MessagePublisherPort;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessagePublisherAdapter implements MessagePublisherPort {

    @Autowired
    private KafkaTemplate<String, String> template; // intelliJ da errore ma in realtà funziona correttamente

    @Override
    @Timed(value="template.msg", description = "timer per invio messaggio", extraTags = {"operation" , "notify"})
    public void notify(String serviceToNotify, String payload) {
        log.debug("invio al servizio: " + serviceToNotify + " il messaggio: " + payload);
        template.send(serviceToNotify, payload);
    }
}
