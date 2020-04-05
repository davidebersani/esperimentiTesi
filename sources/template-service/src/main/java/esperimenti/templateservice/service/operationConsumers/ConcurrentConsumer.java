package esperimenti.templateservice.service.operationConsumers;

import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.OperationsStringParser;
import esperimenti.templateservice.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.javatuples.Triplet;
import java.util.LinkedList;
import java.util.StringTokenizer;

@Component
public class ConcurrentConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException {

        String concurrentOperations = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "[", "]");
        //log.info("operazioni concorrenti: " + concurrentOperations);

        StringTokenizer stConcurrentOperations = new StringTokenizer(concurrentOperations, " ");

        LinkedList<Triplet<String, String, String>> listOfConcurrentOperations = new LinkedList<>();

        String operationType, target, payload;

        while(stConcurrentOperations.countTokens() >= 2){
            operationType = stConcurrentOperations.nextToken();
            target = stConcurrentOperations.nextToken();

            if(!operationType.equals("call") && !operationType.equals("notify"))
                throw new MalformedStringOfOperationsException("token inaspettato: " + operationType + ". In una sequenza di operazioni " +
                        "concorrenti sono ammessi solo: call, notify");

            payload = OperationsStringParser.checkBracketsAndGetWhatsInside(stConcurrentOperations, "{", "}");

            Triplet<String, String, String> operationTriplet = new Triplet<>(operationType, target, payload);
            //log.info("tipo: " + operationTriplet.getValue0() + " target: " + operationTriplet.getValue1() + " payload: " + operationTriplet.getValue2());
            listOfConcurrentOperations.add(operationTriplet);
        }

        if(stConcurrentOperations.hasMoreTokens())
            throw new MalformedStringOfOperationsException("errore di formattazione nella sequenza di operazioni concorrenti");

        templateService.executeConcurrentOperations(listOfConcurrentOperations);
    }
}
