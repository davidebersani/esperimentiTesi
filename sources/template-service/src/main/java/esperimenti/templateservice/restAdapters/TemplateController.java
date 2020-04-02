package esperimenti.templateservice.restAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import esperimenti.templateservice.domain.CallPOJO;
import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@Slf4j
public class TemplateController {

	@Autowired
	private TemplateService templateService;


	@PostMapping(value = "/prosegui", consumes = "application/json")
	public void prosegui(@RequestBody String sequence) throws Exception {
		templateService.parse(sequence);
	}

}
