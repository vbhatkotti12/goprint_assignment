package com.goprint.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;
/**
 *  Message Resolver for Resource Bundle 
 * @author vinod Bhatkotti
 *
 */
@Component
public class MessagePropertyResolver implements MessageSourceAware{
	@Autowired
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;		
	}	
	
	public String getMessage(String code, Object[] args, Locale locale){
		   return messageSource.getMessage(code, args, locale);
	}

}
