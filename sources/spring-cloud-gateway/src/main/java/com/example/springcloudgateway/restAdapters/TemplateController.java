package com.example.springcloudgateway.restAdapters;

import com.example.springcloudgateway.domain.MalformedStringOfOperationsException;
import com.example.springcloudgateway.operationsParsers.OperationsStringParser;
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
		log.debug("stringa ricevuta: " + operations);
		try{
			operationsStringParser.parseOperations(operations);
		}catch (MalformedStringOfOperationsException e) {
			//in caso la porzione di stringa di operazioni elaborata da questo servizio sia malformattata
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}catch (ResponseStatusException e){
			//in caso una successiva chiamata REST ritorni un errore
			throw e;
		}catch (Exception e){
			//in caso si verifichi una qualsiasi altra eccezione nel servizio (anche GeneratedException)
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}