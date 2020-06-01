package esperimenti.templateservice.domain;

public class ConcurrentExecutionException extends Exception {

    public ConcurrentExecutionException(String errorMessage) {
        super(errorMessage);
    }
}
