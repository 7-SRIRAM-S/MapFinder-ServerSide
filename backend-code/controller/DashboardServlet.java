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

import com.mapfinder.dao.CertificateDAO;
import com.mapfinder.dao.UserDAOImpl;
import com.mapfinder.services.AnnouncementManager;
import com.mapfinder.services.CertificateManger;
import com.mapfinder.services.DashBoardManager;
import com.mapfinder.services.LeaderBoardManager;
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
			if(session!=null) {
			}
			
			String[] arr=request.getRequestURI().split("/");
			
			if(arr.length != 4) {
				  res=ResponseUtil.buildResponceError(HttpServletResponse.SC_BAD_REQUEST,"No url Found");  
				  LOGGER.error(new StringBuilder("::: Bad request ::: No Url Found   :::").toString());
			}else {
				if((session=request.getSession(false))!=null) {
					String userName = (String)session.getAttribute("user");
					client_id=(int) UserManager.getIdByName(userName); 
				}

				System.out.println("my sessoin id : "+client_id);
		     if(client_id==-1) {
		    	 System.out.println("problem in session");
		    	 return;
		     }
				if(arr[3].equals("userdetails")) {
					JSONObject json=new JSONObject();
			
					json.put("HINTS", DashBoardManager.getHints(client_id));
					json.put("POINTS", DashBoardManager.getPoint(client_id));
					json.put("CERTIFICATE", DashBoardManager.getCertificateCount(client_id));
			
					res = ResponseUtil.buildResponce(json, "User detail data was processed");
					
			
				}else if(arr[3].equals("topplayers")) {	
					
					res=ResponseUtil.buildResponce(DashBoardManager.getTopPlayers(client_id), "data recived"+client_id);
			
				}else if(arr[3].equals("announcements")) {
			
					res = ResponseUtil.buildResponce(DashBoardManager.getAnnouncent(client_id), "data recived");
				}
				else { 
					LOGGER.error(new StringBuilder("::: Bad request ::: No url found   :::").toString());
					response.sendError(404);
				}
			
			}
			
			
		
		}catch(Exception e) {
		  res=ResponseUtil.buildResponceError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		  LOGGER.error(new StringBuilder("::: Problem in url or parameters are not founded ::: "+e.getMessage()+"   :::").toString());
		}
		
		LOGGER.info(new StringBuilder("::: Data processed (USER ID = "+ client_id +" ) ::: For Uri => "+request.getRequestURI()+" :::").toString());
		
		ResponseUtil.ProcessResponse(res, response);
	}


}
