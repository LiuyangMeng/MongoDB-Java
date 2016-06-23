package com.base;

import org.apache.log4j.Logger;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器基类(抽象类方法)
 * 
 * @author DLHT
 *
 */
public abstract class BaseController {
	protected Logger log = Logger.getLogger(this.getClass());

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleException(MissingServletRequestParameterException ex, HttpServletRequest request) {
		return ClassUtils.getShortName(ex.getClass());
	}

	@ExceptionHandler(NoSuchRequestHandlingMethodException.class)
	public String handleException(NoSuchRequestHandlingMethodException ex, HttpServletRequest request) {
		return ClassUtils.getShortName(ex.getClass());
	}
}
