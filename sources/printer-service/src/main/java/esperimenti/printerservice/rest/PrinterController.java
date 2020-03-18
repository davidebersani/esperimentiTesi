package esperimenti.printerservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import esperimenti.printerservice.domain.CallPOJO;
import esperimenti.printerservice.domain.IPCType;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
public class PrinterController {

	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	private LoadBalancerClient loadBalancer;

	@PostMapping(value = "/print", consumes = "application/json")
	public void print(@RequestBody List<CallPOJO> calls) throws JsonProcessingException {

		for(CallPOJO call:calls) {

			ServiceInstance instance = loadBalancer.choose(call.getService_to_call());

			if(instance!=null) {

				if(call.getIpc_type() == IPCType.REST)
					makeRESTCall(call, instance);

				else if(call.getIpc_type() == IPCType.MESSAGE)
					log.info("Message IPC not yet implemented");
					// TODO: Implementare messagistica

			} else {
				// TODO: Sollevare eccezione
			}
		}
	}

	private void makeRESTCall(CallPOJO call, ServiceInstance instance) throws JsonProcessingException {

		StringBuilder sb = new StringBuilder();
		sb.append(instance.getUri().toString());

		if(call.isGoing_to_fail())
			sb.append("/error");
		else
			sb.append("/print");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(call.getNext_calls());

		//log.info("Body convertito in json: " + body);
		HttpEntity<String> request = new HttpEntity<String>(body, headers);
		log.info("Eseguo chiamata al servizio " + instance.getHost());
		ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(sb.toString(), request, String.class);
		log.info("Risposta chiamata: " + responseEntityStr);
	}

	@PostMapping(value = "/error", consumes = "application/json")
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public void error(@RequestBody List<CallPOJO> calls) {
		log.info("è stato richiamato l'endpoint /error che restituisce 404");
	}

}
