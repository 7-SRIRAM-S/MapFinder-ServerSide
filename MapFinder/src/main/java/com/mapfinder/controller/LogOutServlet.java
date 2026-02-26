package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.services.UserManager;
import com.mapfinder.utils.ResponseUtil;


public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(LogOutServlet.class.getName());


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		LOGGER.trace(new StringBuilder("::: Entering into LogOutServlet ::: GET ::: Going to Logout ::: ").toString());

		HttpSession session = request.getSession(false);

	    if (session != null) {
			LOGGER.trace(new StringBuilder("::: Session Cleared ::: Redirecting into /signin ::: ").toString());
			UserManager.makeDeactiveUser(Integer.parseInt((String)session.getAttribute("user")));
	        session.invalidate(); 
	    }
	    ResponseUtil.ProcessResponse(ResponseUtil.buildSuccessResponse(HttpServletResponse.SC_ACCEPTED, "logout is successful"), response);
	    return;
//	    response.sendRedirect("signin");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

}
