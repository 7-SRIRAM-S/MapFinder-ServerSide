package com.mapfinder.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ValidationUtil;

public class SignInServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(SignInServlet.class.getName());
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			LOGGER.trace(new StringBuilder("::: Entering into SigninServlet ::: GET ::: Check for Session Exist ::: ").toString());

		 	HttpSession session = request.getSession(false);

		 	
		    if (session != null && session.getAttribute("user") != null) {
		    	
				LOGGER.warn(new StringBuilder("Session Found ::: Redirecting /home ::: ").toString());

		        response.sendRedirect("home");
		        return;
		    }

			LOGGER.info(new StringBuilder("No Session Found ::: Redirecting login.html ::: ").toString());

		    response.sendRedirect("login.html");
		    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.trace(new StringBuilder("::: Entering into SigninServlet ::: POST ::: For Login Activity :::").toString());

		JSONObject json=JSONUtil.readAsJSON(request);
		String username=json.optString("username");
		String password=json.optString("password");
		if(ValidationUtil.isNotEmpty(username)&&ValidationUtil.isNotEmpty(password)&&ValidationUtil.isValidPassword(password)) {
			if(UserManager.isValidUser(username, password)) {
				LOGGER.info(new StringBuilder("::: Login Successful ::: "+" Session Set and Redirect /home :::").toString());
				HttpSession session=request.getSession(); 
				session.setAttribute("user", username);
				response.getWriter().write("success");
				return;
			}
			else {
				LOGGER.warn(new StringBuilder("::: Invalid User login ::: For Login Activity :::").toString());
				response.getWriter().write("failed");
				return;
			}
		}
		else {
			LOGGER.warn(new StringBuilder("::: No Data Found invalid request :::  For Login Activity :::").toString());

		}
		
		LOGGER.warn(new StringBuilder("::: Login Failed :::  Redirect into Same Page :::").toString());

		response.getWriter().write("failed");
	}

}
