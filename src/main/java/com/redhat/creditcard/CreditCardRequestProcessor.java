package com.redhat.creditcard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.creditcard.messages.CreditCardRequest;
import com.redhat.creditcard.messages.CreditCardResponse;

public class CreditCardRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		Message out = exchange.getOut();
		boolean valida = false;
		
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		 
		
		Pattern pattern = Pattern.compile(regex);
		
		CreditCardRequest resp = in.getBody(CreditCardRequest.class);
		CreditCardResponse dummyResponse = new CreditCardResponse();
		
		Matcher matcher = pattern.matcher(resp.getNumero());        
        
		
		if(matcher.matches()){
			valida = true;
		}
		
		dummyResponse.setValida(valida);
		
		out.setBody(dummyResponse);
	}

}
