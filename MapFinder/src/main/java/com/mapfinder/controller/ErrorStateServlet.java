package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mapfinder.services.ErrorStateManager;
import com.mapfinder.services.GameModeManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ResponseUtil;

public class ErrorStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			System.out.println("post method working inside errorstate servlet");
		
			JSONObject payload=JSONUtil.readAsJSON(request);
		 	String correctState = payload.optString("correctState", "");
		    String wrongState = payload.optString("wrongState", "");
		    int attemptId = payload.optInt("attemptId", -1);
		    long userId = UserManager.getIdByName(payload.optString("userName", ""));
		    int modeId=GameModeManager.getGameModeIdByAttemptId(attemptId);
		
		    ErrorStateManager.addErrorState(correctState,wrongState,userId,attemptId,modeId);
		System.out.print(correctState+"|"+wrongState+"|"+userId+"|"+attemptId+"|"+modeId);
		
		ResponseUtil.ProcessResponse(payload, response);
	}

}
