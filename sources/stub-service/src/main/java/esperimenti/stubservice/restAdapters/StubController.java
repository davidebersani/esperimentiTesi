package esperimenti.stubservice.restAdapters;

import esperimenti.stubservice.metrics.annotation.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubController {

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    IAMService iamService;

    @GetMapping("/view")
    public String view(@RequestParam Integer projectId, @RequestHeader(name="Authorization") String token) {
        //TODO: Si potrebbe introdurre un'annotazione?
        //TODO: Si potebbe implementare con AOP in modo da separare la logica delle metriche dal resto del codice.

        // Ottengo username da token
        String username = iamService.getUsername(token);

        meterRegistry.counter("views", "projectId", projectId.toString(), "user", username).increment();
        return "L'utente " + username + " ha visitato il progetto " + projectId + "!";
    }
}
