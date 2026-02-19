package com.mapfinder.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class AuthFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private static final Logger	LOGGER = LogManager.getLogger(AuthFilter.class.getName());
	
    public AuthFilter() {
        super();
      
    }


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
			LOGGER.trace(new StringBuilder("::: Entering into Filter ::: Check for Home URL ::: ").toString());
			
			HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;

	        HttpSession session = req.getSession(false);

	        if (session == null || session.getAttribute("user") == null) {
	        	LOGGER.warn(new StringBuilder("::: No Session Found ::: Redirecting into error.jsp :::").toString());
	        	res.sendRedirect("error.jsp");
	            return;
	        }
	        
        	LOGGER.info(new StringBuilder(":::Session Found ::: Redirecting into home.html :::").toString());

	        chain.doFilter(req, res);
		
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
