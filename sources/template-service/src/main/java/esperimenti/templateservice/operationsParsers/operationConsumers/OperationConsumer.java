package esperimenti.templateservice.operationsParsers.operationConsumers;

import java.util.StringTokenizer;

public interface OperationConsumer {
    /**
     * Metodo che consuma la stringa, esegue la prima istruzione contenuta in essa.
     *
     * @param st ovvero il tokenizer posto sul primo parametro dell'operazione
     * @return La stessa stringa senza l'operazione eseguita.
     */
    void consume(StringTokenizer st) throws Exception;
}
