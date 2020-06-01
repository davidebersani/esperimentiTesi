package esperimenti.templateservice.operationsParsers.operationConsumers;

import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.operationsParsers.OperationsStringParser;
import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
@Slf4j
public class NotifyConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException {

        if(!st.hasMoreTokens())
            throw new MalformedStringOfOperationsException("dopo il token 'notify' va indicato il nome del servizio da notificare");

        String serviceToNotify = st.nextToken();

        String payload = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "{", "}");

        log.debug("invio al servizio " + serviceToNotify + " il msg: " + payload);
        //richiama metodo che invia msg a serviceToNotify passandogli payload
        templateService.notifyService(serviceToNotify, payload);
    }
}
