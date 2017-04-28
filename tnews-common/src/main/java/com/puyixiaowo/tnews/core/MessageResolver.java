package com.puyixiaowo.tnews.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {
	
	@Autowired
    private MessageSource reloadableMessageSource;
    
    public void setMessageSource(MessageSource messageSource) {
        this.reloadableMessageSource = messageSource;
    }
    
    public String getMessage(String key){
        return reloadableMessageSource.getMessage(key, new Object[]{} , LocaleContextHolder.getLocale());
    }

    public String getMessage(String key, Object [] params){
        return reloadableMessageSource.getMessage(key, params , LocaleContextHolder.getLocale());
    }

}