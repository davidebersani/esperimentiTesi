package esperimenti.templateservice.service.stringconsumers;

import org.springframework.stereotype.Component;

@Component
public class ExceptionConsumer implements StringConsumer {
    @Override
    public String consume(String s) throws Exception {
        String[] parts = s.split(" ",2);    // Divido la stringa in 3 = [exception, resto della stringa]
        String[] divided = ConsumersUtils.separateBlock(parts[1], '"','"');
        if(! ConsumersUtils.startWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");
        throw new Exception(divided[0]);
    }
}
