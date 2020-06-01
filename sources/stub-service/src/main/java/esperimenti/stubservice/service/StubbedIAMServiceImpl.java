package esperimenti.stubservice.service;

import esperimenti.stubservice.restAdapters.IAMService;

public class IAMServiceImpl implements IAMService {
    @Override
    public String getUsername(String token) {
        return "davide";
    }
}
