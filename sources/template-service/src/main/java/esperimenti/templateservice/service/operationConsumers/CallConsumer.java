package esperimenti.templateservice.service.operationConsumers;

import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.OperationsStringParser;
import esperimenti.templateservice.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;


@Component
public class CallConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException {

        if(!st.hasMoreTokens())
            throw new MalformedStringOfOperationsException("dopo il token 'call' va indicato il nome del servizio da chiamare");

        String serviceToCall = st.nextToken();

        String payload = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "{", "}");
        //log.info("chiamo il servizio " + serviceToCall + " passandogli: " + payload);

        //richiama metodo che fa chiamata rest a serviceToCall passandogli payload
        templateService.callService(serviceToCall, payload);
    }
}
