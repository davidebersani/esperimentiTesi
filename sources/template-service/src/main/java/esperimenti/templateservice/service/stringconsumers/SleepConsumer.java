package esperimenti.templateservice.service.stringconsumers;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SleepConsumer implements StringConsumer{
    @Override
    public String consume(String s) throws Exception {
        String[] parts = s.split(" ",3);    // Divido la stringa in 3 = [sleep, secondi, resto della stringa]
        if(parts.length!=3) {
            throw new Exception("String mal formattata.");
        }
        int sec = Integer.parseInt(parts[1]);
        if(! ConsumersUtils.startWithComma(parts[2].trim())) throw new Exception("Stringa mal formattata. ; mancante.");
        TimeUnit.SECONDS.sleep(sec);

        return parts[2].trim().substring(1);
    }
}
