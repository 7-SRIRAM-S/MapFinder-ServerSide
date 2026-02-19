package com.mapfinder.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.Announcement;
import com.mapfinder.services.AnnouncementManager;
import com.mapfinder.utils.ValidationUtil;

/**
 * Servlet implementation class AnnouncementServlet
 */
public class AnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnouncementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray announcements = AnnouncementManager.getActiveAnnouncement();

		JSONObject responseObject = new JSONObject();
		responseObject.put("data" , announcements);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(responseObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    String jsonString = sb.toString();
	    
	    JSONObject obj = new JSONObject(jsonString);
	    JSONObject responce = new JSONObject();
 	    
	    try {
			String title = obj.getString("title");
			String msg = obj.getString("msg");
			int createdBy = obj.getInt("createdBy");
			boolean isActive = obj.getBoolean("isActive");
			if(ValidationUtil.isNotEmpty(title) && ValidationUtil.isNotEmpty(msg) && createdBy>=0) {
				Announcement notification = new Announcement(title, msg, createdBy,isActive);
			}
			else{
				
			}
			
		} catch (Exception e) {
			
		}

		doGet(request, response);
	}

}
