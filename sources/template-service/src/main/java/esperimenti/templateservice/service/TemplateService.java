package esperimenti.templateservice.service;

import esperimenti.templateservice.domain.GeneratedException;
import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    private TemplateServicePort templateServicePort;

    @Autowired
    private MessagePublisherPort publisher;

    @Timed(value="template.service.operations", description = "timer per call", extraTags = {"operation" , "call"})
    public void callService(String serviceToCall, String payload) {
        templateServicePort.makeRESTcallToService(serviceToCall, payload);
    }


    @Timed(value="template.service.operations", extraTags = {"operation" , "notify"})
    public void notifyService(String serviceToNotify, String payload) {
        publisher.notify(serviceToNotify, payload);
    }


    @Timed(value="template.service.operations",extraTags = {"operation" , "sleep"})
    public void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.info("sono stato interrotto mentre dormivo" + e.toString()); //TODO: non so bene come funziona
        }
    }


    @Timed(value="template.service.operations",extraTags = {"operation" , "exception"})
    public void generateException(String exceptionMessage) throws GeneratedException {
        throw new GeneratedException(exceptionMessage); //TODO: aggiungere nome del servizio che genera l'eccezione
    }

    /**
     *
     * @param listOfConcurrentOperations ArrayList composto da:
     *                                   0: tipo operazione (call/notify/exception/sleep altrimenti ignorata)
     *                                   1: servizio target (per call/notify) - sleep time (per sleep) - messaggio di eccezione (per exception)
     *                                   2: payload (per call/notify)
     */
    @Timed(value="template.service.operations",extraTags = {"operation" , "concurrent"})
    public void executeConcurrentOperations(ArrayList<ArrayList<String>> listOfConcurrentOperations) throws MalformedStringOfOperationsException, InterruptedException {

        List<Runnable> runnableList = new ArrayList<>();

        for(ArrayList<String> op: listOfConcurrentOperations){

            log.info("operazione: " + op.toString());

            // le operazioni con un numero errato di parametri vengono ignorate senza riportare eccezioni //TODO: forse riporta eccezioni
            if(op.size() > 0){

                switch (op.get(0)) {
                    case "call":
                        if(!(op.size() == 3))
                            throw new MalformedStringOfOperationsException("L'operazione call deve essere seguita dal nome del servizio che si " +
                                    "vuole chiamare e dal payload da inviargli");

                        runnableList.add(() -> callService(op.get(1), op.get(2)));
                        break;
                    case "notify":
                        if(!(op.size() == 3))
                            throw new MalformedStringOfOperationsException("L'operazione notify deve essere seguita dal nome del servizio a cui si " +
                                    "vuole inviare il messaggio e dal messaggio stesso");

                        runnableList.add(() -> notifyService(op.get(1), op.get(2)));
                        break;
                    case "exception":
                        if(!(op.size() == 2))
                            throw new MalformedStringOfOperationsException("L'operazione exception deve essere seguita dal messaggio dell'eccezione");

                        runnableList.add(() -> {
                            try {
                                generateException(op.get(1));
                            } catch (GeneratedException e) {
                                log.info("GeneratedException catturata in lambda");
                                throw new RuntimeException("GeneratedException catturata in lambda", e); //TODO: provvisiorio (come riportarla?)
                            }
                        });
                        break;
                    case "sleep":
                        if(!(op.size() == 2))
                            throw new MalformedStringOfOperationsException("L'operazione sleep deve essere seguita dalla durata dello sleep");

                        try {
                            runnableList.add(() -> sleep(Long.parseLong(op.get(1))));
                        }catch(NumberFormatException e){
                            throw new MalformedStringOfOperationsException("dopo il token 'sleep' è richiesto valore un intero che indichi la durata");
                        }
                }
            }
        }

        log.info("eseguo operazioni concorrenti");
        ExecutorService es = Executors.newCachedThreadPool();
        // Aggiungo i task
        for(Runnable task : runnableList)
            es.execute(task);
        // Executor non accetta più task
        es.shutdown();
        // Aspetto che tutti i task vengano portati a termine
        boolean finished = es.awaitTermination(10, TimeUnit.MINUTES);
        //runnableList.parallelStream().forEach(Thread::start); //TODO: aspettare che terminino tutti i threads prima di fare return
        log.info("ho eseguito le operazioni concorrenti");

    }
}