package esperimenti.templateservice.service;

import esperimenti.templateservice.messages.CommandMessage;

public interface MessagePublisherPort {

    public void publish(String message, String service);

}
