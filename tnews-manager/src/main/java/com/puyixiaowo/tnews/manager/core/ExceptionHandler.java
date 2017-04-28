package com.puyixiaowo.tnews.manager.core;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {  
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {
    	//shiro 无权限跳转
    	if (ex.getClass() == UnauthorizedException.class) {
    		return new ModelAndView("error/unauthorized");
    	}
        LOGGER.error("", ex);
        request.setAttribute("error", ex.getMessage());
        
        return new ModelAndView("error/error");
    }
}
