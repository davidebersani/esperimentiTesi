package esperimenti.stub.web.iam;

public interface MessagePublisherPort {

    void notify(String serviceToNotify, String payload);

}
