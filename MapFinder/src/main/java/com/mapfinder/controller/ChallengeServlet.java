package com.mapfinder.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.services.ChallengeManager;
import com.mapfinder.services.NotificationManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.JSONUtil;
import com.mapfinder.utils.ResponseUtil;

/**
 * Servlet implementation class ChallengeServlet
 */
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			JSONArray notifications = ChallengeManager.viewChallenges(client_id);
			responseObject = ResponseUtil.buildResponse(notifications, "notifications board data ");
			ResponseUtil.ProcessResponse(responseObject, response);
		} catch (Exception e) {
			responseObject = ResponseUtil.buildErrorResponse(response.SC_BAD_REQUEST, e.getMessage());
			ResponseUtil.ProcessResponse(responseObject, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject obj = JSONUtil.readAsJSON(request);
		JSONObject resObj = new JSONObject();
		int challengerId = (int) UserManager.getIdByName(obj.optString("challengerName"));
		int opponentId = (int) UserManager.getIdByName(obj.optString("opponentName"));

		if(challengerId<0||opponentId<0) {
			System.out.println("problem in challenge");
			ResponseUtil.ProcessResponse(ResponseUtil.buildErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "id not found"), response);
			return;
		}
		boolean isChallengeCreated = ChallengeManager.makeChallenge(challengerId, opponentId);

		if(isChallengeCreated) {
			if(UserManager.isUserActive(opponentId)) {
				resObj=ResponseUtil.buildResponse(obj, "active");
			}
			else {
				resObj = ResponseUtil.buildResponse(obj, "challenge created successfully");
			}
		}
		else {
			resObj = ResponseUtil.buildResponse(obj, "Challenge Created failed");
		}
		
		ResponseUtil.ProcessResponse(resObj, response);
	}

}
