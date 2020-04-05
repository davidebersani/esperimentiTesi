package esperimenti.templateservice.service;

import esperimenti.templateservice.domain.GeneratedException;
import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.operationConsumers.OperationConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.StringTokenizer;

@Component
@Slf4j
public class OperationsStringParser {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ApplicationContext context;

    public void parseOperations(String operations) throws MalformedStringOfOperationsException, GeneratedException, Exception {
        //tutto minuscolo
        operations = operations.toLowerCase();

        StringTokenizer st = new StringTokenizer(operations," ");
        //log.info("num token generati: " + st.countTokens());

        String token;
        OperationConsumer consumer;

        while (st.hasMoreTokens()) {
            token = st.nextToken();
            //log.info("token processato: " + token);

            try {
                String consumerName = "esperimenti.templateservice.service.operationConsumers." +
                            String.valueOf(token.charAt(0)).toUpperCase() + token.substring(1) + "Consumer";
                consumer = (OperationConsumer) context.getBean(Class.forName(consumerName));

            }catch(ClassNotFoundException e) {
                //log.error("Azione non corretta. Non è stato possibile trovare alcuna azione corrispondente a " + token + ". " +
                // "Sono ammessi solo concurrent, call, notify, exception, sleep");
                throw new MalformedStringOfOperationsException("Azione non corretta. Non è stato possibile trovare alcuna azione corrispondente a "
                        + token + ". Sono ammessi solo concurrent, call, notify, exception, sleep");
            }catch(BeansException e) {
                log.error("Errore nella creazione dell'azione " + token + ". Messaggio: " + e.getMessage());
                throw new Exception("Errore nella creazione dell'azione " + token +".");
            }

            consumer.consume(st);
        }
    }


    /**
     * Dato un tokenizer posizionato sul token corrispondente a openingBracket
     * ritorna la stringa contenuta fino al corrispondente closingBracket escluso (tiene conto dei diversi livelli di annidamento).
     * Il tokenizer verrà avanzato fino al carattere successivo al ; (che deve essere presente) dopo closingBracket.
     *
     * @param st ovvero il tokenizer posto su openingBracket
     * @param openingBracket stringa che indica il simbolo di apertura
     * @param closingBracket stringa che indica il simbolo di chiusura (in caso uguale a openingBracket verrà considerato
     *                       il primo closingBracket incontrato)
     * @return stringa contenuta tra openingBracket e la corrispondente closingBracket
     * @throws MalformedStringOfOperationsException
     * 		- in caso non sia presente la corrispondente closingBracket
     * 		- in caso dopo la corrispondete closingBracket non sia presente il carattere ';'
     */
    public static String checkBracketsAndGetWhatsInside(StringTokenizer st, String openingBracket, String closingBracket) throws MalformedStringOfOperationsException {

        //prelevo il primo token per controllare che sia openingBracket
        if(!st.hasMoreTokens() || !st.nextToken().equals(openingBracket))
            throw new MalformedStringOfOperationsException("parametri dell'operazione mancanti o malformattati (ricorda chedevono essere separate sia dal " +
                    "carattere precedente che successivo da uno spazio)");

        //viene preso fuori per consentire l'utilizzo di openingBracket uguale a closingBracket
        int bracketsCounter = 1;

        StringJoiner sj = new StringJoiner(" ");
        String token;

        while(bracketsCounter!=0 && st.hasMoreTokens()){
            token = st.nextToken();

            if(token.equals(closingBracket)){
                bracketsCounter--;
            }
            else if(token.equals(openingBracket)){
                bracketsCounter++;
            }

            if(bracketsCounter!=0)
                sj.add(token);
        }

        //se non ho trovato la parentesi finale
        if(bracketsCounter!=0)
            throw new MalformedStringOfOperationsException("annidamento parentesi/virgolette malformattate (ricorda chedevono essere separate sia dal " +
                    "carattere precedente che successivo da uno spazio)");

        //se non c'è un prossimo token, oppure se non è ";" la stringa è malformattata
        if(!st.hasMoreTokens() || !st.nextToken().equals(";"))
            throw new MalformedStringOfOperationsException("ogni istruzione deve essere terminata con ';' anche dopo le parentesi/virgolette");

        //log.info("stringa tirata fuori dalle parentesi " + sj.toString());
        //log.info("num token successivi alla parentesi " + st.countTokens());

        if(sj.toString().equals("")){
            //inserisco uno spazio per evitare di passare la stringa vuota nelle successive operazioni
            sj.add(" ");
        }

        return sj.toString();
    }
}
