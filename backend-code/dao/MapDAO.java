//$Id$
package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mapfinder.modal.Map;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class MapDAO {
	private Connection conn = DBUtil.getInstance().getConnection();
	public boolean addMap(Map map) {

        try (
             PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_MAP)) {

            stmt.setString(1, map.getMapName());
            stmt.setString(2, map.getMapType());
            stmt.setBoolean(3, map.isActive());

            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}
	}
	
	
    public List<Map> findAll() {
        List<Map> maps = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(QueryUtil.VIEWMAP);

            while (rs.next()) {
                maps.add(new Map(
                        rs.getInt("map_id"),
                        rs.getString("map_name"),
                        rs.getString("map_type"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return maps;
    }
    
//    ----------- need to change
    public boolean updateMap(boolean isActive, int mapId) {
    	try(Connection conn= DBUtil.getInstance().getConnection();
    	PreparedStatement stmt = conn.prepareStatement(QueryUtil.EDIT_MAP)) {
    		stmt.setBoolean(1, isActive);
    		stmt.setInt(2, mapId);
    		int result = stmt.executeUpdate();
    		return result>0;
    	}
    	catch (Exception e) {
    		return false;
		}
    	
    }
    
//    ------------- need to change -----------
    public boolean deleteMap(int mapId) {
    	try (Connection conn= DBUtil.getInstance().getConnection();
    			PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_MAP)){
    		stmt.setInt(1, mapId);
    		int result = stmt.executeUpdate();
			return result>0;
		} catch (Exception e) {
			return false;
			
		}
    }

}
