package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.mapfinder.services.UserManager;
import com.mapfinder.utils.ResponseUtil;

/**
 * Servlet implementation class SessionServlet
 */
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(SessionServlet.class.getName());

       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject responseJson=null;
		
		LOGGER.trace(new StringBuilder("::: Entering into Session Servlet ::: GET ::: Check for Session Exist ::: ").toString());
		
		HttpSession session = request.getSession(false);

	 	
	    if (session != null && session.getAttribute("user") != null) {
			LOGGER.info(new StringBuilder("Session Found ::: in Backend ::: ").toString());
			responseJson=ResponseUtil.buildSuccessResponse(HttpServletResponse.SC_CONTINUE, UserManager.getUsernameById(Integer.parseInt((String)session.getAttribute("user"))));
	    }
	    
	    else {
	    
	    	LOGGER.warn(new StringBuilder("No Session Found ::: in Backend ::: ").toString());
	    	responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_FORBIDDEN, "session not found");
		
	    }
	    
	    ResponseUtil.ProcessResponse(responseJson, response);
	   
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
