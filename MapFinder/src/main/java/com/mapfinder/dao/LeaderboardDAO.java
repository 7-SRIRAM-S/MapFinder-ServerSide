//$Id$
package com.mapfinder.dao;

import com.mapfinder.modal.Leaderboard;
import com.mapfinder.services.CertificateManger;
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

    public boolean insertLeaderBoad(int userId) throws SQLException {
        

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_LEADERBOARD)) {

            stmt.setInt(1, userId);
           

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
            	int certificate = CertificateManger.userCertificateCount(rs.getInt("user_id"));
            	boolean isFriend = FriendRequestManager.isFriend(userId, rs.getInt("user_id"));
            	boolean isFriendRequested=FriendRequestManager.isAlreadyRequested(userId, rs.getInt("user_id"));
	            	if(isFriendRequested&&(!(isFriend))) {
	            		list.add(new Leaderboard(
	                            	isFriendRequested,
	                            	rs.getInt("leaderboard_id"),
	                                rs.getInt("user_id"),
	                                rs.getInt("total_score"),
	                                rs.getInt("total_games"),
	                                rs.getDouble("average_score"),
	                                rs.getString("username"),
	                                certificate
	            				));
	            	}
	            
            	else {
            		list.add(mapResultSet(rs, certificate, isFriend));
            	}
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        	
        }
        return list;
    }
    
    
//    ===============================================  FindTopFiveLeaderBoard  ===========================================
    
    public List<Leaderboard> findTopFiveLeaderBoard(int userId) {
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


	public boolean updateLeaderBoard(long userId) {
			String sql = "UPDATE leaderboard l " +
	                "SET total_score = (SELECT SUM(score) FROM attempts WHERE user_id = ?), " +
	                "    total_games = (SELECT COUNT(*) FROM attempts WHERE user_id = ?), " +
	                "    average_score = total_score / total_games " +
	                "WHERE l.user_id = ?";

		   try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		       stmt.setLong(1, userId);
		       stmt.setLong(2, userId);
		       stmt.setLong(3, userId);
		
		       int updatedRows = stmt.executeUpdate();
		       return updatedRows > 0;
		   } catch (Exception e) {
		       e.printStackTrace();
		       return false;
		   }
	}
}
