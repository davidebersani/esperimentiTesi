package esperimenti.templateservice.restAdapters;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudController {

    @Autowired
    MeterRegistry meterRegistry;

    @GetMapping("/view")
    public String view(@RequestParam Integer projectId, @RequestParam String utente) {
        //TODO: Si potrebbe introdurre un'annotazione
        meterRegistry.counter("views", "projectId", projectId.toString(), "user", utente).increment();
        return "L'utente " + utente + " ha visitato il progetto " + projectId + "!";
    }
}
