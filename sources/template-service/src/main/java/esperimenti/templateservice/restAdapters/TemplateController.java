package esperimenti.templateservice.restAdapters;

import esperimenti.templateservice.domain.GeneratedException;
import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.OperationsStringParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class TemplateController {

	@Autowired
	OperationsStringParser operationsStringParser;

	@PostMapping(value = "/prosegui", consumes = "text/plain")
	public void prosegui(@RequestBody String operations){
		//log.info("stringa ricevuta: " + operations);
		try{
			operationsStringParser.parseOperations(operations);
		}catch (MalformedStringOfOperationsException e){
			log.info("exception: " + e.toString());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}catch (GeneratedException e){
			log.info("exception: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}