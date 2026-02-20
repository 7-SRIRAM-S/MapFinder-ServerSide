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
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.SessionUtil;
import com.mapfinder.utils.ValidationUtil;

public class SignUpServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(SignUpServlet.class.getName());


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.trace(new StringBuilder("::: Entering into SignUpServlet ::: GET ::: Check for Session Exist ::: ").toString());

		
		HttpSession session = request.getSession(false);

	 	
	    if (session != null && session.getAttribute("user") != null) {
			LOGGER.warn(new StringBuilder("Session Found ::: Redirecting /home ::: ").toString());
	        response.sendRedirect("home");
	        return;
	    }
	    
		LOGGER.info(new StringBuilder("No Session Found ::: Redirecting signup.html ::: ").toString());

		response.sendRedirect("signup.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LOGGER.trace(new StringBuilder("::: Entering into SignUpServlet ::: POST ::: For Login Activity :::").toString());

		JSONObject json=JSONUtil.readAsJSON(request);
		String username=json.optString("username");
		String password=json.optString("password");
		if(ValidationUtil.isNotEmpty(username)) {
			if(UserManager.isUserNameExist(username)) {
				LOGGER.warn(new StringBuilder("::: Duplicate username entrolled :::  For Signup Activity :::").toString());
				response.getWriter().write("duplicate");
				return;
			}
			if(ValidationUtil.isNotEmpty(password)&&ValidationUtil.isValidPassword(password)) {
				if(UserManager.addUser(username, password)) {
					LOGGER.info(new StringBuilder("::: Register Successful ::: "+" Session Set and Redirect /home :::").toString());
					HttpSession session=request.getSession(); 
					session.setAttribute("user", username);
					response.getWriter().write("success");
					return;
				}
				else {
					response.getWriter().write("failed");
					return;
				}
			}
		}
		else {
			LOGGER.warn(new StringBuilder("::: No Data Found invalid request :::  For Signup Activity :::").toString());

		}
		
		LOGGER.warn(new StringBuilder("::: Register Failed :::  Redirect into Same Page :::").toString());

		response.getWriter().write("failed");
	}

}
