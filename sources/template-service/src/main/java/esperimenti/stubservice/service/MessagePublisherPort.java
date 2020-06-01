package esperimenti.stubservice.service;

public interface MessagePublisherPort {

    void notify(String serviceToNotify, String payload);

}
