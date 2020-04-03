package esperimenti.templateservice.service;

import esperimenti.templateservice.service.stringconsumers.ConcurrentConsumer;
import esperimenti.templateservice.service.stringconsumers.StringConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    ApplicationContext context;

    /**
     * Metodo che interpreta ed esegue le chiamate descritte dalla stringa s;
     *
     * @param s stringa che descrive la sequenza di chiamate
     * @throws Exception
     */
    public void parse(String s) throws Exception {
        StringConsumer consumer = null;
        // Finchè la stringa non è vuota (ci sono comandi) vado avanti
        while(!s.trim().isEmpty() && !s.equals(" ")) {
            s = s.trim();   // Tolgo possibili spazi all'inizio della stringa che potrebbero darmi problemi
            String[] parts = s.split(" ", 2);   // Divido la stringa in [comando, resto della stringa]
            // Creo il comando
            try {
                if (parts[0].equals("["))
                    consumer = context.getBean(ConcurrentConsumer.class);
                else {
                    String consumerName = "esperimenti.templateservice.service.stringconsumers." +
                            String.valueOf(parts[0].charAt(0)).toUpperCase() +
                            parts[0].substring(1).toLowerCase() +
                            "Consumer";
                    consumer = (StringConsumer) context.getBean(Class.forName(consumerName));
                }
            }catch(NoSuchBeanDefinitionException e) {
                log.error("Azione non corretta. Non è stato possibile trovare alcuna azione corrispondente. Messaggio:" + e.getMessage());
            }catch(BeansException e) {
                log.error("Errore nella creazione dell'azione. Messaggio: " + e.getMessage());
            }
            if(consumer==null) {
                throw new Exception("Errore nell'esecuzione dell'azione.");
            }else {
                s = consumer.consume(s);
            }

        }

    }


}