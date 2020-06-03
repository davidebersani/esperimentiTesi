package esperimenti.stub.operationsParsers.operationConsumers;

import java.util.StringTokenizer;

public interface OperationConsumer {
    /**
     * Metodo che consuma la prima istruzione contenuta nello StringTokenizer.
     *
     * @param st ovvero il tokenizer posto sul primo parametro dell'operazione
     */
    void consume(StringTokenizer st) throws Exception;
}
