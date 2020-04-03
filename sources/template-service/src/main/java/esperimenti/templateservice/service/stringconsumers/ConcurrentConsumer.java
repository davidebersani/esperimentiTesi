package esperimenti.templateservice.service.stringconsumers;

import esperimenti.templateservice.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConcurrentConsumer implements StringConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public String consume(String s) throws Exception {
        String[] divided = ConsumersUtils.separateBlock(s,'[',']');
        if(! ConsumersUtils.startWithComma(divided[1].trim())) throw new Exception("Stringa mal formattata. ; mancante.");
        templateService.parse(divided[0]);
        return divided[1].trim().substring(1);
    }
}
