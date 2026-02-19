//$Id$
package com.mapfinder.dao;


import com.mapfinder.modal.Attempts;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttemptDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
    public boolean inserAttempt(Attempts attempt) {

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_ATTEMPT)) {

            stmt.setInt(1, attempt.getUserId());
            stmt.setInt(2, attempt.getMapId());
            stmt.setInt(3, attempt.getModeId());
            stmt.setInt(4, attempt.getScore());
            stmt.setInt(5, attempt.getTotalScore());
            stmt.setInt(6, attempt.getCorrectAnswer());
            stmt.setInt(7, attempt.getWrongAnswer());
            stmt.setDate(8, (Date) attempt.getStartedAt());
            stmt.setDate(9, (Date) attempt.getEndedAt());
            stmt.setInt(10, attempt.getDurationSeconds());

            stmt.executeUpdate();
            return true;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
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
