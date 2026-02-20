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
import com.mapfinder.utils.ResponseUtil;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		JSONArray announcements = AnnouncementManager.getActiveAnnouncement(userId);
		String message = "all announcement fetched";
		JSONObject responseObject = new JSONObject();
		responseObject = ResponseUtil.buildResponce(announcements, message);
		ResponseUtil.ProcessResponse(responseObject, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		String jsonString = sb.toString();

		JSONObject obj = new JSONObject(jsonString);
		String message = "";
		JSONObject responce = new JSONObject();
		try {
			String title = obj.getString("title");
			String msg = obj.getString("msg");
			int createdBy = obj.getInt("createdBy");
			boolean isActive = obj.getBoolean("isActive");
			if (ValidationUtil.isNotEmpty(title) && ValidationUtil.isNotEmpty(msg) && createdBy >= 0) {

				boolean isAnnouncementAdded = AnnouncementManager.addAnnouncement(title, msg, createdBy, isActive);

				if (isAnnouncementAdded) {
					message = "Announcement Added";
					responce = ResponseUtil.buildResponce(obj, msg);
				} else {
					message = "Announcement didn't added";
					responce = ResponseUtil.buildResponceError(406, message);

				}
			} else {
				message = "input invalid empty data found";
				responce = ResponseUtil.buildResponceError(406, message);
			}

		} catch (Exception e) {
			message = "input invalid empty data found";
			responce = ResponseUtil.buildResponceError(500, message);
		}
		
		ResponseUtil.ProcessResponse(responce, response);
		
	}

}
