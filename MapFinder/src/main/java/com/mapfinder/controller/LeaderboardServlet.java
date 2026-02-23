package com.mapfinder.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.Leaderboard;
import com.mapfinder.modal.Map;
import com.mapfinder.services.LeaderBoardManager;
import com.mapfinder.services.MapManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.ResponseUtil;

/**
 * Servlet implementation class LeaderboardServlet
 */
public class LeaderboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			int client_id = -1;
			JSONObject responseObject = new JSONObject();
			try {
				HttpSession session = null;
				session = request.getSession(false);
	
				if ((session = request.getSession(false)) != null) {
					String userName = (String) session.getAttribute("user");
					client_id = Integer.parseInt(userName);
				}
	
				if (client_id == -1) {
					System.out.println("problem in session");
					return;
				}
	
				JSONArray leaderBoardList = LeaderBoardManager.viewLeaderBoard(client_id);
				responseObject = ResponseUtil.buildResponse(leaderBoardList, "leader board data ");
				ResponseUtil.ProcessResponse(responseObject, response);
			} catch (Exception e) {
				responseObject = ResponseUtil.buildErrorResponse(response.SC_BAD_REQUEST, e.getMessage());
				ResponseUtil.ProcessResponse(responseObject, response);
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
