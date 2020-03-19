package esperimenti.templateservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import esperimenti.templateservice.domain.CallPOJO;
import esperimenti.templateservice.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@PostMapping(value = "/prosegui", consumes = "application/json")
	public void prosegui(@RequestBody List<CallPOJO> calls) throws JsonProcessingException{
		templateService.prosegui(calls);
	}

	@PostMapping(value = "/errore", consumes = "application/json")
	public void errore(@RequestBody List<CallPOJO> calls) {
		try{
			templateService.errore(calls);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "hai chiamato /errore e ti becchi un 500");
		}
	}

}
