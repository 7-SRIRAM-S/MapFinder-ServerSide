//$Id$
package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mapfinder.modal.Gamemode;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class GameModeDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
    public boolean insertMode(Gamemode mode) {

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_MODE)) {

            stmt.setString(1, mode.getModeName());
            stmt.setString(2, mode.getText());
            stmt.setInt(3, mode.getTimeLimitSeconds());
            stmt.setBoolean(4, mode.getIsRanked());

            int result = stmt.executeUpdate();
            return result>0;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}
    }
    
    
    
//    =============================   view Game modes   ==========================================
    
    public List<Gamemode> viewGameModes(){
    	List<Gamemode> gamemodes = new ArrayList<Gamemode>();

		try (Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(QueryUtil.VIEW_MODES);
            while(rs.next()) {
            	gamemodes.add(new Gamemode(rs.getInt("mode_id"),rs.getString("mode_name") , rs.getString("description"), rs.getInt("time_limit_seconds"),rs.getBoolean("is_ranked"),rs.getDate("created_at")));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return gamemodes;
    }
    
    
    
//	===============================================  delete location ==================================================
	
    public boolean deleteLocation(int modeId) {
    	try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_MODE)){
    		stmt.setInt(1, modeId);
    		int result = stmt.executeUpdate();	
			return result>0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
    }



	public int getGameModeIdByAttemptId(int attemptId) {
		int modeId = -1; 
	    String query = "SELECT mode_id FROM attempts WHERE attempt_id = ?";

	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, attemptId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            modeId = rs.getInt("mode_id");
	        }
	        rs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return modeId;

	}
    
    
}
