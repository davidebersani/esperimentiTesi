package esperimenti.stub.web.iam;

public interface TemplateServicePort {

    /***
     * Chiamata REST che propaga un eventuale fallimento
     * @param serviceToCall
     * @param payload
     */
    void makeRESTcallToService(String serviceToCall, String payload);

    /***
     * Chiamata REST che NON propaga un eventuale fallimento
     * @param serviceToCall
     * @param payload
     */
    void makeSafeRESTcallToService(String serviceToCall, String payload);

}
