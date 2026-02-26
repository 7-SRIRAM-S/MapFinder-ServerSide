//$Id$
package com.mapfinder.dao;


import com.mapfinder.modal.Attempts;
import com.mapfinder.services.PreparedStatement;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttemptDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
    public int inserAttempt(Attempts attempt) {

    	int attemptId = -1;

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_ATTEMPT,Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, attempt.getUserId());
            stmt.setInt(2, attempt.getModeId());


            int rows =stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    attemptId = rs.getInt(1);
                }
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return attemptId;
    }

    public List<Attempts> findByUserId(int userId) throws SQLException {
        List<Attempts> attempts = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.ATTEMPT_FINDBY_USERID)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attempts.add(mapResultSet(rs));
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return attempts;
    }
    
    public boolean updateAttempt(int score, int attemptId) {
    	String sql = "UPDATE attempts " +
                "SET score = score + ? WHERE attempt_id = ?";

		   try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		       stmt.setInt(1, score);                 
		       stmt.setInt(2, attemptId);              
		             
		       int updatedRows = stmt.executeUpdate();
		       return updatedRows > 0;
		   } catch (Exception e) {
		       e.printStackTrace();
		       return false;
		   }
	}
    
    public boolean finalizeAttempt(int attemptId) {

    	String sql = "UPDATE attempts a " +
                "SET  " +
                "    a.wrong_answers = (SELECT COUNT(*) FROM error_states e WHERE e.attempt_id = ?), " +
                "    a.ended_at = NOW() " +
                "WHERE a.attempt_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	      
	        stmt.setInt(1, attemptId);       
	        stmt.setInt(2, attemptId);  

	        int updatedRows = stmt.executeUpdate();
	        return updatedRows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    private Attempts mapResultSet(ResultSet rs) throws SQLException {
        return new Attempts(
                rs.getInt("attempt_id"),
                rs.getInt("user_id"),
                rs.getInt("map_id"),
                rs.getInt("mode_id"),
                rs.getInt("score"),
                rs.getInt("total_questions"),
                rs.getInt("correct_answers"),
                rs.getInt("wrong_answers"),
                rs.getTimestamp("started_at"),
                rs.getTimestamp("ended_at"),
                rs.getInt("duration_seconds")
        );
    }

	
}
