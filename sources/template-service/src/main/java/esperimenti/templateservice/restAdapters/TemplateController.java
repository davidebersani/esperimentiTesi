package esperimenti.templateservice.restAdapters;

import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.operationsParsers.OperationsStringParser;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;

@RestController
@Slf4j
public class TemplateController {

	@Autowired
	OperationsStringParser operationsStringParser;

	@Autowired
	MeterRegistry meterRegistry;

	@PostMapping(value = "/prosegui", consumes = "text/plain")
	public void prosegui(@RequestBody String operations){
		//log.info("stringa ricevuta: " + operations);
		Instant start = Instant.now();
		try{
			operationsStringParser.parseOperations(operations);
			registerLatencyMetric(start);
		}catch (MalformedStringOfOperationsException e) {
			//in caso la porzione di stringa di operazioni elaborata da questo servizio sia malformattata
			registerLatencyMetric(start);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}catch (ResponseStatusException e){
			registerLatencyMetric(start);
			//in caso una successiva chiamata REST ritorni un errore
			throw e;
		}catch (Exception e){
			registerLatencyMetric(start);
			//in caso si verifichi una qualsiasi altra eccezione nel servizio (anche GeneratedException)
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private void registerLatencyMetric(Instant start) {
		Timer timer = meterRegistry.timer("http.server.requests.latency.sec");
		Duration duration = Duration.between(start, Instant.now());
		timer.record(duration);
	}
}