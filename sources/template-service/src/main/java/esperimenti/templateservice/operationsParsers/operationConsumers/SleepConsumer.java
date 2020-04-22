package esperimenti.templateservice.operationsParsers.operationConsumers;

import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
@Slf4j
public class SleepConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException {

        if(!st.hasMoreTokens())
            throw new MalformedStringOfOperationsException("dopo il token 'sleep' è richiesto un valore intero che indichi la durata");

        long sleepTime;

        try {
            sleepTime = Long.parseLong(st.nextToken());
        }catch(NumberFormatException e) {
            throw new MalformedStringOfOperationsException("dopo il token 'sleep' è richiesto valore un intero che indichi la durata");
        }

        log.debug("metto il servizio in sleep per " + sleepTime + " mSec");
        if(!st.hasMoreTokens() || !st.nextToken().equals(";"))
            throw new MalformedStringOfOperationsException("ogni comando deve essere terminato con il carattere ';' anche sleep");

        //richiama metodo che mette in sleep il servizio
        templateService.sleep(sleepTime);
    }
}
