package esperimenti.templateservice.restAdapters;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubController {

    @Autowired
    MeterRegistry meterRegistry;

    @GetMapping("/view")
    public String view(@RequestParam Integer projectId, @RequestParam String user) {
        //TODO: Si potrebbe introdurre un'annotazione
        //TODO: Prendere username da header http

        // Estraggo bearer token da header http

//        meterRegistry.counter("views", "projectId", projectId.toString(), "user", user).increment();
        return "L'utente " + user + " ha visitato il progetto " + projectId + "!";
    }
}
