package esperimenti.templateservice.service;

public interface MessagePublisherPort {

    public void publish(String message, String service);

}
