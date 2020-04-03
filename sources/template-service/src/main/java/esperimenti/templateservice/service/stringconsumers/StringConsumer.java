package esperimenti.templateservice.service.stringconsumers;

public interface StringConsumer {
    /**
     * Metodo che consuma la stringa, esegue la prima istruzione contenuta in essa.
     *
     * @param s Stringa che descrive le chiamate da eseguire
     * @return La stessa stringa senza l'operazione eseguita.
     */
    public String consume(String s) throws Exception;
}
