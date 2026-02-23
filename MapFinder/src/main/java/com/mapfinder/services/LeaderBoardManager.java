//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.mapfinder.dao.LeaderboardDAO;
import com.mapfinder.modal.Leaderboard;
import com.mapfinder.utils.JSONUtil;

public class LeaderBoardManager {
	private static LeaderboardDAO leaderBoard = new LeaderboardDAO();
    private static final Logger LOGGER=LogManager.getLogger(LeaderBoardManager.class.getName());
    
    
    public static boolean insertLeaderBoard(int leaderboardId, int userId, int mapId, int modeId, int totalScore, int totalGame, double averageScore, int rankPosition) {
    	LOGGER.trace(new StringBuilder("::: Add Leaderboard into DB :::  Creating Object for LeaderBoard ::: ").toString());
    	Leaderboard leader = null;
    	try {
    		
    		leader = new Leaderboard(leaderboardId, userId, mapId, modeId, totalScore, totalGame, averageScore, rankPosition);
    		
    		return LeaderBoardManager.leaderBoard.insertLeaderBoad(leader);
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return false;
		}
    }
    
    public static int getTotalScore(int userId) {
    	try {
    		return leaderBoard.getTotalScore(userId);
    	}
    	catch(Exception e) {
    		LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
        	return 0;
    	}
    }
    
    
    public static JSONArray viewLeaderBoard(int userId){
    	List<Leaderboard> leaders = new ArrayList<>();
    	try {
			leaders = LeaderBoardManager.leaderBoard.viewAllLeaderboards(userId);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return JSONUtil.convertLeaderBoardToJson(leaders);
    }
    
    public static JSONArray topFiveLeaderBoard(int userId){
    	List<Leaderboard> leaders = new ArrayList<>();
    	try {
    		leaders = LeaderBoardManager.leaderBoard.findTopFiveLeaderBoard(userId);
    	}
    	catch(Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
    		
    	}
    	return JSONUtil.convertLeaderBoardToJson(leaders);
    }
    
}
