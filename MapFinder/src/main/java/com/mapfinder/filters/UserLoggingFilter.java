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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.ThreadContext;


public class UserLoggingFilter extends HttpFilter implements Filter {
       

	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 try {
	            HttpServletRequest req = (HttpServletRequest) request;
	            HttpSession session = req.getSession(false);

	            String userId = "guest";
	            if (session != null) {
	                Object uidObj = session.getAttribute("user");
	                if (uidObj != null) {
	                    userId = uidObj.toString();
	                }
	            }
	            ThreadContext.put("user", userId); 
	            chain.doFilter(request, response);
	        } finally {
	            ThreadContext.clearAll();
	        }
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
