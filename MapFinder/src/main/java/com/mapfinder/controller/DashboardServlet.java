package com.mapfinder.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.mapfinder.services.DashBoardManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.ResponseUtil;

public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(DashboardServlet.class.getName());
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		
		JSONObject res=new JSONObject();
		int client_id = -1;
		HttpSession session=null;

		try {
			
			session=request.getSession(false);

			String[] arr=request.getRequestURI().split("/");
			
			if(arr.length != 4) {
				  res=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST,"No url Found");  
				  LOGGER.error(new StringBuilder("::: Bad request ::: No Url Found   :::").toString());
			}else {
				if(session!=null) {
					String userId = (String) session.getAttribute("user");
					client_id=Integer.parseInt(userId); 
					 if(client_id==-1) {
						  LOGGER.fatal(new StringBuilder("::: No User id found  ::: Inside Session :::").toString());
				    	 return;
				     }
				}
				else {
					  LOGGER.fatal(new StringBuilder(":::No  Session availble ::: Redirecting into error.jsp :::").toString());
				}
			    
				if(arr[3].equals("userdetails")) {
					
					LOGGER.info(new StringBuilder("::: Get data for userdetails ::: querying into DashBoardManager :::").toString());

					JSONObject json=new JSONObject();
					json.put("USERNAME", UserManager.getUsernameById(client_id));
					json.put("HINTS", DashBoardManager.getHints(client_id));
					json.put("POINTS", DashBoardManager.getPoint(client_id));
					json.put("CERTIFICATE", DashBoardManager.getCertificateCount(client_id));
			
					res = ResponseUtil.buildResponse(json, "User detail data was processed");
					
			
				}else if(arr[3].equals("topplayers")) {	
					
					res=ResponseUtil.buildResponse(DashBoardManager.getTopPlayers(client_id), "data received"+client_id);
			
				}else if(arr[3].equals("announcements")) {
			
					res = ResponseUtil.buildResponse(DashBoardManager.getAnnouncent(client_id), "data recived");
				}
				else { 
					LOGGER.error(new StringBuilder("::: Bad request ::: No url found   :::").toString());
					response.sendError(404);
				}
			
			}
			
			
		
		}catch(Exception e) {
		  res=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		  LOGGER.error(new StringBuilder("::: Problem in url or parameters are not founded ::: "+e.getMessage()+"   :::").toString());
		}
		
		
		ResponseUtil.ProcessResponse(res, response);
	}


}
