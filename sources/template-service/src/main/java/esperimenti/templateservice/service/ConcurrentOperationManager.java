package esperimenti.templateservice.service;

import esperimenti.templateservice.domain.ConcurrentExecutionException;
import esperimenti.templateservice.domain.GeneratedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@Slf4j
public class ConcurrentOperationManager {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ExecutorService executor;

    private List<Runnable> taskList;

    public ConcurrentOperationManager() {
        taskList = new LinkedList<>();
    }

    public void addCallTask(String service, String payload) {
        Runnable r = () -> templateService.callService(service,payload);
        this.taskList.add(r);
    }

    public void addExceptionTask(String message) {
        Runnable r = () -> {
            try {
                templateService.generateException(message);
            } catch (GeneratedException e) {
                log.info("GeneratedException catturata in lambda");
                throw new RuntimeException("GeneratedException catturata in lambda: GeneratedException: " + e.getMessage(), e); //TODO: provvisiorio (come riportarla?)
            }
        };
        this.taskList.add(r);
    }

    public void addSleepTask(Long millisec) {
        Runnable r = () -> templateService.sleep(millisec);
        this.taskList.add(r);
    }

    public void addNotifyTask(String service, String payload) {
        Runnable r = () -> templateService.notifyService(service,payload);
        this.taskList.add(r);
    }

    public void executeTasksAndCheckTheResult() throws ConcurrentExecutionException, InterruptedException {
        // Copio la lista dei thread da eseguire e pulisco la lista principale
        List<Runnable> toExecute = taskList;
        taskList=new LinkedList<>();

        List<Future> taskResults = new LinkedList<>();
        // Aggiungo i task
        for(Runnable task : toExecute) {
            taskResults.add(executor.submit(task));
        }

        // Executor non accetta più task
        executor.shutdown();

        // Controllo che l'esecuzione di tutti i thread sia andata a buon fine
        for(Future f : taskResults) {
            try {
                f.get();
            } catch (ExecutionException e) {
                Exception exception = (Exception) e.getCause();
                throw new ConcurrentExecutionException("Si è verificata un'eccezione nell'esecuzione di un thread.", exception);
            }
        }

    }
}