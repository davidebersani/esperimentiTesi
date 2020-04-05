package esperimenti.templateservice.service.operationConsumers;

public class ConsumersUtils {
    /**
     * Controlla che la stringa inizi con un ;, e che quindi l'istruzione si sia chiusa effettivamente con un ;
     * @param s
     * @return
     * @throws Exception
     */
    public static boolean startWithComma(String s) throws Exception {
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
    public static String[] separateBlock(String s, char openingBracket, char closingBracket) throws Exception {
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
    public static int getFineBlocco(String s,char openingBracket, char closingBracket) throws Exception {
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
