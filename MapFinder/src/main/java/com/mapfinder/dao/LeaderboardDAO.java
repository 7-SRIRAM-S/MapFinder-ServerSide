//$Id$
package com.mapfinder.dao;

import com.mapfinder.modal.Leaderboard;
import com.mapfinder.services.FriendRequestManager;
import com.mapfinder.services.UserManager;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
//	=======================================  insert leaderboard  ====================================================

    public boolean insertLeaderBoad(Leaderboard lb) throws SQLException {
        

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_LEADERBOARD)) {

            stmt.setInt(1, lb.getLeaderboardId());
            stmt.setInt(2, lb.getUserId());
            stmt.setInt(3, lb.getMapId());
            stmt.setInt(4, lb.getTotalScore());
            stmt.setInt(5, lb.getTotalGame());
            stmt.setDouble(6, lb.getAverageScore());
            stmt.setInt(7, lb.getRankPosition());

            int result = stmt.executeUpdate();
            return result>0;
            
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
    
    public int getTotalScore(int userId) {
    	int totalScore=0;
    	try(PreparedStatement stmt = conn.prepareStatement(QueryUtil.GET_TOTALSCORE)){
    		stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            totalScore = rs.getInt("total_Score");
	        }
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        return totalScore;
    }

    
//  ================================================  FindAll LeaderBoard  ======================================================
    
    public List<Leaderboard> viewAllLeaderboards(int userId)  {
        List<Leaderboard> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(QueryUtil.VIEW_LEADERBOARD);
            while (rs.next()) {
            	int certificate = (int) UserManager.getHint(rs.getInt("user_id"));
            	boolean isFriend = FriendRequestManager.isFriend(userId, rs.getInt("user_id"));
            	
                list.add(mapResultSet(rs, certificate, isFriend));
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        	
        }
        return list;
    }
    
    
//    ===============================================  FindTopFiveLeaderBoard  ===========================================
    
    public List<Leaderboard> findTopFiveLeaderBoard(int userId){
    	List<Leaderboard> list = new ArrayList<>();
    	try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(QueryUtil.VIEW_TOPFIVE_LEADERBOARD);

            while (rs.next()) {
            	int certificate = (int) UserManager.getHint(rs.getInt("user_id"));
            	boolean isFriend = FriendRequestManager.isFriend(userId, rs.getInt("user_id"));
            	
                list.add(mapResultSet(rs, certificate, isFriend));
            }
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    
    
    
// --------------------------------------------------- mapResultSet ----------------------------------------------------------------

    private Leaderboard mapResultSet(ResultSet rs , int certificate , boolean isFriend) throws SQLException {
        return new Leaderboard(
                rs.getInt("leaderboard_id"),
                rs.getInt("user_id"),
                rs.getInt("total_score"),
                rs.getInt("total_games"),
                rs.getDouble("average_score"),
                rs.getString("username"),
                certificate,
                isFriend
        );
    }
}
