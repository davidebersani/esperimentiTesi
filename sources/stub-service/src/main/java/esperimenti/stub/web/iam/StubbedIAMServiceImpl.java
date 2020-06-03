package esperimenti.stub.web.iam;

import esperimenti.stub.web.api.IAMService;
import org.springframework.stereotype.Service;

@Service
public class StubbedIAMServiceImpl implements IAMService {
    @Override
    public String getUsername(String token) {
        return token;
    }
}
