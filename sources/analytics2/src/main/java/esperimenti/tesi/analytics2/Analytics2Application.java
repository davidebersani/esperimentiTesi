package esperimenti.tesi.analytics2;

import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.influxdb.client.InfluxDBClient;

@SpringBootApplication
public class Analytics2Application {

	public static void main(String[] args) {
		SpringApplication.run(Analytics2Application.class, args);
	}

	@Value("${analytics.influxdb.host}")
	private String host;

	@Bean
	public InfluxDBClient getFluxClient() {
		return InfluxDBClientFactory.create(host,"".toCharArray(),"org");
	}

}
