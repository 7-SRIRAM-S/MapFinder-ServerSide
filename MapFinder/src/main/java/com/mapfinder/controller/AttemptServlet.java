package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mapfinder.services.AttemptManager;
import com.mapfinder.services.GameModeManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ResponseUtil;

/**
 * Servlet implementation class AttemptServlet
 */
public class AttemptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId=null;
		JSONObject responseJson=null;
		HttpSession session=request.getSession(false);
		
		String mode = request.getParameter("mode");

		int modeId = GameModeManager.getGameModeId(mode);

		if(session!=null) {
			userId=(String)session.getAttribute("user");
		}
		if(session==null||userId==null) {
			System.out.println("session not found "+session);

			responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "invalid request");
		}
		else if (modeId == -1) {
			
			System.out.println("mode not found");

		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Mode");
		  
		}
		else {
			int attemptId=AttemptManager.addAttempt(Integer.parseInt(userId), modeId);
			JSONObject attemptJson=new JSONObject();
			attemptJson.put("attemptId", attemptId);
			responseJson=ResponseUtil.buildResponse(attemptJson, "attempt registered successfully");
		}
		
		ResponseUtil.ProcessResponse(responseJson, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject payload=new JSONObject();
		payload=JSONUtil.readAsJSON(request);
	    int score = payload.optInt("userScore", 0); 
	    int attemptId = payload.optInt("attemptId", -1);

	   boolean isSaved= AttemptManager.updateAttempt(score, attemptId);
	    System.out.println(isSaved);
	    ResponseUtil.ProcessResponse(payload, response);
	}

}
