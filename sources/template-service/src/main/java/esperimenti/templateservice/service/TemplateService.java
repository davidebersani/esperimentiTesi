package esperimenti.templateservice.service;

import esperimenti.templateservice.domain.GeneratedException;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    TemplateServicePort templateServicePort;

    @Autowired
    private MessagePublisherPort publisher;

    public void callService(String serviceToCall, String payload) {
        templateServicePort.makeRESTcallToService(serviceToCall, payload);
    }

    public void notifyService(String serviceToNotify, String payload) {
        publisher.notify(serviceToNotify, payload);
    }

    public void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.info("sono stato interrotto mentre dormivo" + e.toString()); //TODO: non so bene come funziona
        }
    }

    public void generateException(String exceptionMessage) throws GeneratedException {
        throw new GeneratedException(exceptionMessage); //TODO: aggiungere nome del servizio che genera l'eccezione
    }

    /**
     *
     * @param listOfConcurrentOperations tripletta composta da:
     *                                   0: tipo operazione (call/notify altrimenti ignorata)
     *                                   1: servizio target
     *                                   2: payload
     */
    public void executeConcurrentOperations(LinkedList<Triplet<String, String, String>> listOfConcurrentOperations) {

        List<Thread> threadList = new ArrayList<>();

        for(Triplet<String,String,String> op: listOfConcurrentOperations){
            if (op.getValue0().equals("call"))
                threadList.add( new Thread( () -> callService(op.getValue1(), op.getValue2()) ) );
            else if(op.getValue0().equals("notify"))
                threadList.add( new Thread( () -> notifyService(op.getValue1(), op.getValue2()) ) );
        }

        log.info("eseguo operazioni concorrenti");
        threadList.parallelStream().forEach(Thread::start); //TODO: aspettare che terminino tutti i threads prima di fare return
        log.info("ho eseguito le operazioni concorrenti");

    }
}