package esperimenti.templateservice.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class KafkaHealthIndicator  implements HealthIndicator {

    private KafkaTemplate<String, String> kafka;

    public KafkaHealthIndicator(@Autowired KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    /**
     * Return an indication of health.
     *
     * @return the health for
     */
    @Override
    public Health health() {
        try {
            kafka.send("kafka-health-indicator", "❥").get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return Health.down(e).build();
        }
        return Health.up().build();
    }
}
