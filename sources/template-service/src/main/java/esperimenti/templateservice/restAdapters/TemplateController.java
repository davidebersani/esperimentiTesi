package esperimenti.templateservice.restAdapters;

import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
