package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mapfinder.services.GameModeManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.ResponseUtil;

public class GamemodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId=null;
		JSONObject responseJson=null;
		HttpSession session=request.getSession(false);
		
		String mode = request.getParameter("mode");

		int modeId = GameModeManager.getGameModeId(mode);

		if (modeId == -1) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Mode");
		  
		}
		
		if(session!=null) {
			userId=(String)session.getAttribute("user");
		}
		if(session==null||userId==null) {
			responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "invalid request");
		}
		else {
			responseJson=GameModeManager.getGameDetails(Integer.parseInt(userId));
		}
		
		ResponseUtil.ProcessResponse(responseJson, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
