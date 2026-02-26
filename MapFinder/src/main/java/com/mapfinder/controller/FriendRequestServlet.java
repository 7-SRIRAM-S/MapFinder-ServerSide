package com.mapfinder.controller;

import java.io.IOException;
import java.util.Arrays;

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
		String requestURI=request.getRequestURI();
		JSONObject responseJson=new JSONObject();
		String[] requests=requestURI.split("/");
		HttpSession session=request.getSession(false);
		int userId;
		if(session==null) {
			responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_FORBIDDEN, "no session found");
		}
		else {
			if(session.getAttribute("user")==null) {
				responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_FORBIDDEN, "no user found");
			}
			else {
				userId=Integer.parseInt((String)session.getAttribute("user"));
				if(requests[3].equals("getrequests")) {
					responseJson=ResponseUtil.buildResponse(FriendRequestManager.getFriendRequests(userId),"friend request sent");
				}
				else if(requests[3].equals("accept")) {
					int friendId=Integer.parseInt(request.getParameter("friendId"));
					boolean isAccepted=FriendRequestManager.acceptFriendRequest(userId, friendId);
					if(isAccepted) {
						responseJson=ResponseUtil.buildSuccessResponse(HttpServletResponse.SC_ACCEPTED, "friend request accepted");
					}
					else {
						responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "no frd-request found");
					}
				}
				else if(requests[3].equals("reject")) {
					int friendId=Integer.parseInt(request.getParameter("friendId"));
					boolean isRejected=FriendRequestManager.rejectFriendRequest(userId, friendId);
					if(isRejected) {
						responseJson=ResponseUtil.buildSuccessResponse(HttpServletResponse.SC_ACCEPTED, "friend request rejected");
					}
					else {
						responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "no frd-request found");
					}
				}
				else {
					responseJson=ResponseUtil.buildErrorResponse(HttpServletResponse.SC_EXPECTATION_FAILED, "failed");
				}
			}
		}
		
		ResponseUtil.ProcessResponse(responseJson, response);
		
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
