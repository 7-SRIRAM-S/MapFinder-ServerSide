//$Id$
package com.mapfinder.dao;

import com.mapfinder.modal.ErrorState;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorStatesDAO {
	private Connection conn = DBUtil.getInstance().getConnection();
    
    public boolean insertErrorState(ErrorState error) {
        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_ERROR_STATE)) {

            stmt.setLong(1, error.getUserId());
            stmt.setLong(2, error.getModeId());
            stmt.setLong(3, error.getStateId());
            stmt.setString(4, error.getWrongAnswer());
            stmt.setString(5, error.getCorrectAnswer());
            stmt.setInt(6, error.getAttemptNumber());

            stmt.executeUpdate();
            return true;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }

    public List<ErrorState> findByUser(long userId) throws SQLException {
        List<ErrorState> list = new ArrayList<>();
        

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.VIEW_ERROR_STATE)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new ErrorState(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("mode_id"),
                        rs.getInt("location_id"),
                        rs.getString("wrong_answer"),
                        rs.getString("correct_answer"),
                        rs.getInt("attempt_number"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
            }
        }
        return list;
    }
}
