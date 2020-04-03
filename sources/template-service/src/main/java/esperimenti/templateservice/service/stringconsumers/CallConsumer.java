package esperimenti.templateservice.service.stringconsumers;

import esperimenti.templateservice.service.TemplateServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CallConsumer implements StringConsumer {

    @Autowired
    TemplateServicePort templateServicePort;

    @Override
    /**
     * Interpreta ed esegue l'azione call (Chiamata rest)
     * @param s stringa che descrive la chiamata e le cose successive da fare
     * @return la stessa stringa s passata come parametro senza la chiamata effettuata
     * @throws Exception
     */
    public String consume(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [call, servizio, resto della stringa]
        if(parts.length!=3) {
            throw new Exception("String mal formattata.");
        }
        // parts[0] Dovrebbe essere "call"
        String service = parts[1];
        String[] divided = ConsumersUtils.separateBlock(parts[2].trim(),'{','}');
        if(! ConsumersUtils.startWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");

        // Faccio la chiamata
        System.out.println("Eseguo chiamata a " + service + " con parametro: " + divided[0]);
        this.templateServicePort.proseguiVersoServizio(divided[0],service);
        String stringWithoutMyAction =  divided[1].trim().substring(1); // RESTO della stringa senza il punto e virgola finale
        return stringWithoutMyAction;
    }
}
