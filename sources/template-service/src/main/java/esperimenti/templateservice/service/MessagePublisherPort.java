package esperimenti.templateservice.service;

public interface MessagePublisherPort {

    void notify(String serviceToNotify, String payload);

}
