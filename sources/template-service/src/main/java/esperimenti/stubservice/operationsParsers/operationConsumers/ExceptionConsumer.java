package esperimenti.stubservice.operationsParsers.operationConsumers;

import esperimenti.stubservice.domain.GeneratedException;
import esperimenti.stubservice.domain.MalformedStringOfOperationsException;
import esperimenti.stubservice.operationsParsers.OperationsStringParser;
import esperimenti.stubservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
@Slf4j
public class ExceptionConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException, GeneratedException {

        String exceptionMessage = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "\"", "\"");

        log.debug("genero eccezione con messaggio: " + exceptionMessage);
        //richiama metodo che genera eccezione e passa exceptionMessage
        templateService.generateException(exceptionMessage);
    }
}

