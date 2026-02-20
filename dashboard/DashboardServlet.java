package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.mapfinder.dao.UserDAOImpl;
import com.mapfinder.services.DashboardManager;
import com.mapfinder.utils.ResponseUtil;

public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER=LogManager.getLogger(DashboardServlet.class.getName());
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LOGGER.trace(new StringBuilder("::: Entering into Dashboard Servlet ::: GET ::: ").toString());


		JSONObject responseJson=new JSONObject();
		int client_id = -1;
		try {
			
			String[] arr=request.getRequestURI().split("/");
			
			if(arr.length != 4) {
				responseJson=ResponseUtil.buildResponceError(HttpServletResponse.SC_BAD_REQUEST,"No url Found");  
				  LOGGER.error(new StringBuilder("::: Bad request ::: No Url Found   :::").toString());
			}else {
				
				client_id=Integer.parseInt(request.getParameter("user_id")); 
		
				if(arr[3].equals("userdetails")) {
					JSONObject json=new JSONObject();
			
					json.put("HINTS", DashboardManager.getHints(client_id));
					json.put("POINTS", DashboardManager.getPoint(client_id));
					json.put("CERTIFICATE", DashboardManager.getCertificateCount(client_id));
			
					responseJson = ResponseUtil.buildResponce(json, "User detail data was processed");
					
			
				}else if(arr[3].equals("topplayers")) {	
					
					responseJson=ResponseUtil.buildResponce(DashboardManager.getTopPlayers(), "data recived"+client_id);
			
				}else if(arr[3].equals("announcements")) {

					int limit = Integer.parseInt(request.getParameter("limit"));
			
					responseJson = ResponseUtil.buildResponce(DashboardManager.getMessage(client_id, limit), "data recived");
				}
				else { 
					LOGGER.error(new StringBuilder("::: Bad request ::: No url found   :::").toString());
					response.sendError(404);
				}
			
			}
		
		}catch(Exception e) {
		responseJson=ResponseUtil.buildResponceError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		  LOGGER.error(new StringBuilder("::: Problem in url or parameters are not founded ::: "+e.getMessage()+"   :::").toString());
		}
		
		LOGGER.info(new StringBuilder("::: Data processed (USER ID = "+ client_id +" ) ::: For Uri => "+request.getRequestURI()+" :::").toString());
		
		ResponseUtil.ProcessResponse(responseJson, response);
	}


}
