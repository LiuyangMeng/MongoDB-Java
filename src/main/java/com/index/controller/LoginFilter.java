package com.index.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {
	
	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * 过滤销毁时执行
	 */
	public void destroy() {
		log.info("LoginFilter destroy");
	}

	/**
	 * 过滤器执行主体
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("Filter doFilter:"+request.getLocalAddr());
		chain.doFilter(request, response);
		
	}

	/**
	 * 过滤器初始化时执行
	 */
	public void init(FilterConfig arg0) throws ServletException {
		log.info("LoginFilter init");
	}

}
