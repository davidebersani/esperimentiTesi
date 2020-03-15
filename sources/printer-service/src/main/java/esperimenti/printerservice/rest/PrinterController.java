package esperimenti.printerservice.rest;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PrinterController {
	private final Logger logger = Logger.getLogger(PrinterController.class.toString()); 
	
	@Value("${spring.application.name}")
	private String appName;

	@GetMapping("/")
	public String print() {
		return "Io sono " + appName;
	}
}
