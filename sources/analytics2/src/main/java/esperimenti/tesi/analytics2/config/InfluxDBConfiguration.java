package esperimenti.tesi.analytics2.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {

    @Value("${spring.influxdb2.url}")
    private String host;

    @Bean
    public InfluxDBClient getFluxClient() {
        return InfluxDBClientFactory.create(host,"".toCharArray(),"org");
    }

}
