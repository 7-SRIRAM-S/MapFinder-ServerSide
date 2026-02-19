package com.mapfinder.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.Leaderboard;
import com.mapfinder.modal.Map;
import com.mapfinder.services.LeaderBoardManager;
import com.mapfinder.services.MapManager;

/**
 * Servlet implementation class LeaderboardServlet
 */
public class LeaderboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaderboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray leaderBoardList = LeaderBoardManager.viewLeaderBoard();
		JSONObject responseObject = new JSONObject();
		responseObject.put("data" , leaderBoardList);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.setStatus(200);
	    response.getWriter().write(responseObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
