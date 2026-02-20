package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.utils.SessionUtil;

public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(LogOutServlet.class.getName());


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.trace(new StringBuilder("::: Entering into LogOutServlet ::: GET ::: Going to Logout ::: ").toString());

		HttpSession session = request.getSession(false);

	    if (session != null) {
			LOGGER.trace(new StringBuilder("::: Session Cleared ::: Redirecting into /signin ::: ").toString());
	        session.invalidate(); 
	    }
	    response.getWriter().write("logout");
	    return;
//	    response.sendRedirect("signin");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

}
