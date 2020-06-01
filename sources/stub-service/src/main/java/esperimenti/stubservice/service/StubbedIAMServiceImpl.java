package esperimenti.stubservice.service;

import esperimenti.stubservice.restAdapters.IAMService;
import org.springframework.stereotype.Service;

@Service
public class StubbedIAMServiceImpl implements IAMService {
    @Override
    public String getUsername(String token) {
        return "dav.bersani@prova.com";
    }
}
