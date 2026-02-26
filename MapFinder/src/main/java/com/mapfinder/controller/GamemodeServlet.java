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
import com.mapfinder.services.LeaderBoardManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
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
		
		int attemptId=-1;
		try {
            JSONObject payload = JSONUtil.readAsJSON(request);

            String userName = payload.getString("userName");
            int hints = payload.getInt("hintsCount");
            boolean isGameFinished=payload.getBoolean("isGameFinished");
            
            if(userName == null ) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            HttpSession session = request.getSession(false);
            if(session==null||session.getAttribute("attemptId")==null) {
    			ResponseUtil.ProcessResponse(ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "invalid request"),response);
    			return;
    		}
            attemptId=Integer.parseInt((String)session.getAttribute("attemptId"));

            long userId = UserManager.getIdByName(userName);

            LeaderBoardManager.updateLeaderboard(userId);
            AttemptManager.finalizeAttempt(attemptId);
            UserManager.updateHint(hints,(int)userId);
            if(isGameFinished) {
            	UserManager.increaseHints(attemptId);
            }
            
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
		
	}

}
