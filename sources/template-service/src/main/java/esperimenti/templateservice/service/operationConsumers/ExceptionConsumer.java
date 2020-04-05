package esperimenti.templateservice.service.operationConsumers;

import esperimenti.templateservice.domain.GeneratedException;
import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.OperationsStringParser;
import esperimenti.templateservice.service.TemplateService;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
public class ExceptionConsumer implements OperationConsumer {

    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException, GeneratedException {
        String exceptionMessage = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "\"", "\"");
        //log.info("genero eccezione con messaggio: " + exceptionMessage);

        //richiama metodo che genera eccezione e passa exceptionMessage
        templateService.generateException(exceptionMessage);
    }
}

