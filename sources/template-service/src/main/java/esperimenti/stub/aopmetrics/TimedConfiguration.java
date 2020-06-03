package esperimenti.stub.aopmetrics;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimedConfiguration {
    @Bean
    public TimedAspect timedAspect(@Autowired MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}