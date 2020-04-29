package com.example.springcloudgateway.operationsParsers.operationConsumers;

import com.example.springcloudgateway.domain.MalformedStringOfOperationsException;
import com.example.springcloudgateway.operationsParsers.OperationsStringParser;
import com.example.springcloudgateway.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.StringTokenizer;

@Component
@Slf4j
public class SafecallConsumer implements OperationConsumer {

    @Autowired
    TemplateService templateService;

    @Override
    public void consume(StringTokenizer st) throws MalformedStringOfOperationsException {

        if(!st.hasMoreTokens())
            throw new MalformedStringOfOperationsException("dopo il token 'safecall' va indicato il nome del servizio da chiamare");

        String serviceToCall = st.nextToken();

        String payload = OperationsStringParser.checkBracketsAndGetWhatsInside(st, "{", "}");

        log.debug("chiamo il servizio " + serviceToCall + " passandogli: " + payload);
        //richiama metodo che fa chiamata safe rest a serviceToCall passandogli payload
        templateService.safecallService(serviceToCall, payload);
    }
}
