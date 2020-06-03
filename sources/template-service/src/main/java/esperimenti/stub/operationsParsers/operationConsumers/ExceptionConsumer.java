package esperimenti.stub.operationsParsers.operationConsumers;

import esperimenti.stub.domain.GeneratedException;
import esperimenti.stub.domain.MalformedStringOfOperationsException;
import esperimenti.stub.operationsParsers.OperationsStringParser;
import esperimenti.stub.web.iam.TemplateService;
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

