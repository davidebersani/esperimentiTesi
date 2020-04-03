package esperimenti.templateservice.service.stringconsumers;

import esperimenti.templateservice.service.MessagePublisherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyConsumer implements StringConsumer {

    @Autowired
    private MessagePublisherPort publisher;

    public NotifyConsumer(MessagePublisherPort publisher) {
        this.publisher=publisher;
    }

    @Override
    public String consume(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [notify, servizio, resto della stringa]
        if(parts.length!=3) {
            throw new Exception("String mal formattata.");
        }
        // parts[0] Dovrebbe essere "notify"
        String service = parts[1];
        String[] divided = ConsumersUtils.separateBlock(parts[2].trim(),'{','}');
        if(! ConsumersUtils.startWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");

        // Invio il messaggio
        System.out.println("Invio messaggio asincrono a " + service + " con parametro: " + divided[0]);
        this.publisher.publish(divided[0],service);
        String stringWithoutMyAction =  divided[1].trim().substring(1); // RESTO della stringa senza il punto e virgola finale
        return stringWithoutMyAction;
    }
}
