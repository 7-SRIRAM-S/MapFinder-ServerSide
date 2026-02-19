package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class SessionServlet
 */
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(SessionServlet.class.getName());

       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.trace(new StringBuilder("::: Entering into Session Servlet ::: GET ::: Check for Session Exist ::: ").toString());
		
		HttpSession session = request.getSession(false);

	 	
	    if (session != null && session.getAttribute("user") != null) {
			LOGGER.info(new StringBuilder("Session Found ::: in Backend ::: ").toString());

	        response.getWriter().write("success");
	        return;
	    }
	    
		LOGGER.warn(new StringBuilder("No Session Found ::: in Backend ::: ").toString());


	    response.getWriter().write("failed");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
