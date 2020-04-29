package com.example.springcloudgateway.service;

import com.example.springcloudgateway.domain.ConcurrentExecutionException;
import com.example.springcloudgateway.domain.GeneratedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private ApplicationContext context;

    private List<Runnable> taskList;

    public ConcurrentOperationManager() {
        taskList = new LinkedList<>();
    }

    public void addCallTask(String service, String payload) {
        Runnable r = () -> templateService.callService(service,payload);
        this.taskList.add(r);
    }

    public void addSafecallTask(String service, String payload) {
        Runnable r = () -> templateService.safecallService(service,payload);
        this.taskList.add(r);
    }

    public void addExceptionTask(String message) {
        Runnable r = () -> {
            try {
                templateService.generateException(message);
            } catch (GeneratedException e) {
                log.info("GeneratedException catturata in lambda");
                throw new RuntimeException("GeneratedException catturata: " + e.getMessage(), e);
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

        List<ExecutionException> exceptionsList = new ArrayList<>();

        ExecutorService executor = context.getBean(ExecutorService.class);

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
                log.debug("Si è verificata un'eccezione nell'esecuzione di un thread." + exception);
                exceptionsList.add(e);
            }
        }

        if(!exceptionsList.isEmpty()){

            StringBuilder allExceptions = new StringBuilder();

            exceptionsList.forEach(exc-> {
                allExceptions.append(exc.toString());
                allExceptions.append(" ");
            });

            throw new ConcurrentExecutionException("Si sono verificate le seguenti eccezioni nell'esecuzione dei thread paralleli: " + allExceptions.toString() );
        }

    }
}
