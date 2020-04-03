package esperimenti.templateservice.restAdapters;

import esperimenti.templateservice.domain.GeneratedException;
import esperimenti.templateservice.domain.MalformedStringOfOperationsException;
import esperimenti.templateservice.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.StringTokenizer;

@RestController
@Slf4j
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@PostMapping(value = "/prosegui", consumes = "text/plain")
	public void prosegui(@RequestBody String operations){

		//log.info("stringa ricevuta: " + operations);
		parseOperations(operations);
	}

	private void parseOperations(String operations) {
		//tutto minuscolo
		operations = operations.toLowerCase();

		StringTokenizer st = new StringTokenizer(operations," ");
		//log.info("num token generati: " + st.countTokens());
		String token;

		try {
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				//log.info("token processato: " + token);
				switch (token) {
					case ("concurrent"):
						processConcurrent(st);
						break;
					case ("call"):
						processCall(st);
						break;
					case ("notify"):
						processNotify(st);
						break;
					case ("exc"):
						processExc(st);
						break;
					case ("sleep"):
						processSleep(st);
						break;
					default:
						throw new MalformedStringOfOperationsException("token inaspettato: " + token + ". Sono ammessi solo: concurrent, call, notify, exc, sleep");
				}
			}
		}catch (MalformedStringOfOperationsException e){
			//log.info("exception: " + e.toString());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}catch (GeneratedException e){
			//log.info("exception: " + e.toString());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private void processConcurrent(StringTokenizer st) throws MalformedStringOfOperationsException {

		String concurrentOperations = checkBracketsAndGetWhatsInside(st, "[", "]");
		//log.info("operazioni concorrenti: " + concurrentOperations);

		StringTokenizer stConcurrentOperations = new StringTokenizer(concurrentOperations, " ");

		LinkedList<Triplet<String, String, String>> listOfConcurrentOperations = new LinkedList<>();

		String operationType, target, payload;

		while(stConcurrentOperations.countTokens() >= 2){
			operationType = stConcurrentOperations.nextToken();
			target = stConcurrentOperations.nextToken();

			if(!operationType.equals("call") && !operationType.equals("notify"))
				throw new MalformedStringOfOperationsException("token inaspettato: " + operationType + ". In una sequenza di operazioni " +
						"concorrenti sono ammessi solo: call, notify");

			payload = checkBracketsAndGetWhatsInside(stConcurrentOperations, "{", "}");

			Triplet<String, String, String> operationTriplet = new Triplet<>(operationType, target, payload);
			//log.info("tipo: " + operationTriplet.getValue0() + " target: " + operationTriplet.getValue1() + " payload: " + operationTriplet.getValue2());
			listOfConcurrentOperations.add(operationTriplet);
		}

		if(stConcurrentOperations.hasMoreTokens())
			throw new MalformedStringOfOperationsException("errore di formattazione nella sequenza di operazioni concorrenti");

		templateService.executeConcurrentOperations(listOfConcurrentOperations);
	}

	private void processCall(StringTokenizer st) throws MalformedStringOfOperationsException {

		if(!st.hasMoreTokens())
			throw new MalformedStringOfOperationsException("dopo il token 'call' va indicato il nome del servizio da chiamare");

		String serviceToCall = st.nextToken();

		String payload = checkBracketsAndGetWhatsInside(st, "{", "}");
		//log.info("chiamo il servizio " + serviceToCall + " passandogli: " + payload);

		//richiama metodo che fa chiamata rest a serviceToCall passandogli payload
		templateService.callService(serviceToCall, payload);

	}

	//TODO FORSE: metti assieme call e notify
	private void processNotify(StringTokenizer st) throws MalformedStringOfOperationsException {

		if(!st.hasMoreTokens())
			throw new MalformedStringOfOperationsException("dopo il token 'notify' va indicato il nome del servizio da notificare");

		String serviceToNotify = st.nextToken();

		String payload = checkBracketsAndGetWhatsInside(st, "{", "}");
		//log.info("invio al servizio " + serviceToNotify + " il msg: " + payload);

		//richiama metodo che invia msg a serviceToNotify passandogli payload
		templateService.notifyService(serviceToNotify, payload);
	}

	private void processSleep(StringTokenizer st) throws MalformedStringOfOperationsException {

		if(!st.hasMoreTokens())
			throw new MalformedStringOfOperationsException("dopo il token 'sleep' è richiesto valore un intero che indichi la durata");

		try{
			long sleepTime = Integer.parseInt(st.nextToken());
			//log.info("metto il servizio in sleep per " + sleepTime + " mSec");

			//richiama metodo che mette in sleep il servizio
			templateService.sleep(sleepTime);
		}catch(NumberFormatException e){
			throw new MalformedStringOfOperationsException("dopo il token 'sleep' è richiesto valore un intero");
		}

		if(!st.hasMoreTokens() || !st.nextToken().equals(";"))
			throw new MalformedStringOfOperationsException("ogni comando deve essere terminato con il carattere ';' anche sleep");
	}

	private void processExc(StringTokenizer st) throws MalformedStringOfOperationsException, GeneratedException {

		String exceptionMessage = checkBracketsAndGetWhatsInside(st, "\"", "\"");
		//log.info("genero eccezione con messaggio: " + exceptionMessage);

		//richiama metodo che genera eccezione e passa exceptionMessage
		templateService.generateException(exceptionMessage);
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
	private String checkBracketsAndGetWhatsInside(StringTokenizer st, String openingBracket, String closingBracket) throws MalformedStringOfOperationsException {

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
			throw new MalformedStringOfOperationsException("annidamento parentesi malformattate (ricorda chedevono essere separate sia dal " +
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