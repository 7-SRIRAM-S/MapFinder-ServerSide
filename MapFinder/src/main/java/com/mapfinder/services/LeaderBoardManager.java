//$Id$
package com.mapfinder.services;

import java.sql.SQLException;
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
    
    public static boolean insertLeaderboard(int userId) throws SQLException {
    	return leaderBoard.insertLeaderBoad(userId);
    }
    
    
    public static boolean updateLeaderboard(long userId) {
        return leaderBoard.updateLeaderBoard(userId);
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
