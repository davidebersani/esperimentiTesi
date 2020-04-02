package esperimenti.templateservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    TemplateServicePort templateServicePort;

    @Autowired
    private MessagePublisherPort publisher;

    /**
     * Metodo che interpreta ed esegue le chiamate descritte dalla stringa s;
     *
     * @param s stringa che descrive la sequenza di chiamate
     * @throws Exception
     */
    public void parse(String s) throws Exception {
        // Finchè la stringa non è vuota (ci sono comandi) vado avanti
        while(!s.trim().isEmpty() && !s.equals(" ")) {
            s = s.trim();   // Tolgo possibili spazi all'inizio della stringa che potrebbero darmi problemi
            String[] parts = s.split(" ", 2);   // Divido la stringa in [comando, resto della stringa]

            switch (parts[0]) {
                case "call":
                    s = call(s);    // Ogni metodo ritorna la stringa s senza la parte che ha eseguito lui. Quindi se s = call A { call B { }; }; il metodo ritorna call B { };
                    break;

                case "[":
                    s = concurrent(s);
                    break;

                case "notify":
                    s = notify(s);
                    break;

                case "exception":
                    s = exception(s);

                case "sleep":
                    s = sleep(s);
                    break;

                default:
                    throw new Exception("Nessuna azione riconosciuta.");
            }
        }

    }

    private String sleep(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [sleep, secondi, resto della stringa]
        int sec = Integer.parseInt(parts[1]);
        TimeUnit.SECONDS.sleep(sec);
        if(! terminateWithComma(parts[2].trim())) throw new Exception("Stringa mal formattata. ; mancante.");

        return parts[2].trim().substring(1);
    }

    private String exception(String s) throws Exception {
        String[] parts = s.split(" ",2);    // Divido la stringa in 3 = [exception, resto della stringa]
        String[] divided = separateBlock(parts[1], '"','"');
        if(! terminateWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");
        throw new Exception(divided[0]);
    }

    private String notify(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [notify, servizio, resto della stringa]
        if(parts.length!=3) {
            throw new Exception("String mal formattata.");
        }
        // parts[0] Dovrebbe essere "notify"
        String service = parts[1];
        String[] divided = separateBlock(parts[2].trim(),'{','}');
        if(! terminateWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");

        // Invio il messaggio
        System.out.println("Invio messaggio asincrono a " + service + " con parametro: " + divided[0]);
        this.publisher.publish(divided[0],service);
        String stringWithoutMyAction =  divided[1].trim().substring(1); // RESTO della stringa senza il punto e virgola finale
        return stringWithoutMyAction;
    }

    private String concurrent(String s) throws Exception {
        String[] divided = separateBlock(s,'[',']');
        if(! terminateWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");
        parse(divided[0]);
        return divided[1].trim().substring(1);
    }

    /**
     * Interpreta ed esegue l'azione call (Chiamata rest)
     * @param s stringa che descrive la chiamata e le cose successive da fare
     * @return la stessa stringa s passata come parametro senza la chiamata effettuata
     * @throws Exception
     */
    private String call(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [call, servizio, resto della stringa]
        if(parts.length!=3) {
            throw new Exception("String mal formattata.");
        }
        // parts[0] Dovrebbe essere "call"
        String service = parts[1];
        String[] divided = separateBlock(parts[2].trim(),'{','}');
        if(! terminateWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");

        // Faccio la chiamata
        System.out.println("Eseguo chiamata a " + service + " con parametro: " + divided[0]);
        this.templateServicePort.proseguiVersoServizio(divided[0],service);
        String stringWithoutMyAction =  divided[1].trim().substring(1); // RESTO della stringa senza il punto e virgola finale
        return stringWithoutMyAction;
    }

    private boolean terminateWithComma(String s) throws Exception {
        char charToCheck;
        try {
            charToCheck = s.charAt(0);   // Controllo che dopo il blocco di istruzioni ci sia un ;
        } catch (StringIndexOutOfBoundsException e) {
            throw new Exception("Stringa mal formattata. ; mancante.");
        }
        return (charToCheck==';');
    }


    /**
     * Separa il blocco dal resto della stringa
     * @param s stringa
     * @return  array di 2 posizioni: pos 0 = countenuto del blocco senza parentesi, pos 1 = resto della stringa
     * @throws Exception
     */
    private String[] separateBlock(String s, char openingBracket, char closingBracket) throws Exception {
        int end = getFineBlocco(s,openingBracket, closingBracket);  // Estraggo l'indice della parentesi di chiusura
        String message = s.substring(1,end);     // Estraggo il contenuto del blocco (tolgo primo e ultimo carattere)
        String rest = s.substring(end+1);    // resto della stringa, togliendo tutto il blocco di istruzioni

        return new String[] {message,rest};
    }

    /**
     *
     * @param s stringa che inizia con il blocco di istruzioni
     * @return Indice della parentesi di chiusura
     * @throws Exception
     */
    private int getFineBlocco(String s,char openingBracket, char closingBracket) throws Exception {
        if(s.charAt(0)!=openingBracket)
            throw new Exception("String mal formattata. Il primo carattere non è una parentesi.");

        int count=1;    // Conteggio delle parentesi
        int i=1;        // Indice per scorrere la stringa
        while(i<s.length() && count>0) {
            if(s.charAt(i)==closingBracket)     // Se è una parentesi chiusa
                count--;
            else if(s.charAt(i)==openingBracket)    // Se è una parentesi aperta
                count++;
            i++;
        }

        if(count==0)    // Se ho effettivamente trovato la parentesi corrispondente, ritorno con successo
            return i-1;
        else
            throw new Exception("String mal formattata.");
    }

}