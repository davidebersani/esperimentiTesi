package esperimenti.templateservice.restAdapters;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudController {

    @GetMapping("/view")
    public String view() {
        return "Mi hai visitato!";
    }
}
