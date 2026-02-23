package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.mapfinder.services.FriendRequestManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ResponseUtil;

public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("get method working");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			JSONObject payload=JSONUtil.readAsJSON(request);
			String userId=null;
			JSONObject responseJson=null;
			String userName=payload.getString("userName");
			String friendName=payload.getString("friendName");
			
			HttpSession session=request.getSession(false);
			if(session!=null) {
				userId=(String) session.getAttribute("user");
			}
			if(session==null||userId==null||
			UserManager.getIdByName(userName)!=Integer.parseInt(userId)||UserManager.getIdByName(friendName)==-1) {
				responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "invalid user request");
			}
			else {
				int uId=Integer.parseInt(userId);
				int fId=(int)UserManager.getIdByName(friendName);
				if(FriendRequestManager.isAlreadyRequested(uId, fId)) {
					responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_CONFLICT, "already friend requested");
				}
				else if(FriendRequestManager.isFriend(uId, fId)) {
					responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_CONFLICT, "already in friendlist");
				}
				else {
					boolean isAdded=FriendRequestManager.addFriend(fId, uId);
					responseJson=ResponseUtil.buildSuccessResponse(HttpServletResponse.SC_ACCEPTED, "friend request processed");
				}
			}
			
			ResponseUtil.ProcessResponse(responseJson, response);
	}

}
