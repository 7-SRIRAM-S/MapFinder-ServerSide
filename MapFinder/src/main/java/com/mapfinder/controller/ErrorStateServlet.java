package com.mapfinder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.ErrorState;
import com.mapfinder.services.ErrorStateManager;
import com.mapfinder.services.GameModeManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ResponseUtil;

public class ErrorStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String escapeCsv(String value) {
	    if (value == null) {
	        return "";
	    }

	    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
	        value = value.replace("\"", "\"\"");
	        return "\"" + value + "\"";
	    }

	    return value;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject responseJson=new JSONObject();
		HttpSession session=request.getSession(false);
		int userId=0;
		if(session==null) {
			responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_FORBIDDEN, "no session found");
		}
		else {
			if(session.getAttribute("user")==null) {
				responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_FORBIDDEN, "no user found");
			}
			else {
				userId=Integer.parseInt((String)session.getAttribute("user"));
				String isDownload=request.getParameter("download");
				if(isDownload!=null) {
		
					List<ErrorState> errorStates = ErrorStateManager.getErrorStateById(userId);
				    response.setContentType("text/csv");
				    response.setHeader("Content-Disposition", "attachment; filename=\"weaker_states.csv\"");
		
				    PrintWriter writer = response.getWriter();
		
				    writer.println("S.No,Weaker_State,Elected_State"); 
				    int idx=1;
				    for (ErrorState state : errorStates) {
				        writer.println(
				            idx++ + "," +
				            escapeCsv(state.getWrongAnswer()) + "," +
				            escapeCsv(state.getCorrectAnswer()) 
				        );
				    }
		
				    writer.flush();
				    writer.close();
				    return;
				}
				
	
					JSONArray errorStates=JSONUtil.convertErrorStateToJson(ErrorStateManager.getErrorStateById(userId));

					
					ResponseUtil.ProcessResponse(
								ResponseUtil.buildResponse(errorStates, "success")
								, response);
					return;
			}
		}
		
		ResponseUtil.ProcessResponse(responseJson, response);
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			JSONObject payload=JSONUtil.readAsJSON(request);
			
		 	String correctState = payload.optString("correctState", "");
		    String wrongState = payload.optString("wrongState", "");
		    int attemptId = payload.optInt("attemptId", -1);
		    long userId = UserManager.getIdByName(payload.optString("userName", ""));
		    
		    HttpSession session = request.getSession(false);
            if(session==null||session.getAttribute("attemptId")==null) {
    			ResponseUtil.ProcessResponse(ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "invalid request"),response);
    			return;
    		}
            attemptId=Integer.parseInt((String)session.getAttribute("attemptId"));
		    
		    
		    int modeId=GameModeManager.getGameModeIdByAttemptId(attemptId);
		
		    ErrorStateManager.addErrorState(correctState,wrongState,userId,attemptId,modeId);
		
		ResponseUtil.ProcessResponse(payload, response);
	}

}
