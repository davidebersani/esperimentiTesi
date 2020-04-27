package esperimenti.templateservice.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    @Scope("prototype")
    public ExecutorService getExecutor() {
        return Executors.newCachedThreadPool();
    }
}
