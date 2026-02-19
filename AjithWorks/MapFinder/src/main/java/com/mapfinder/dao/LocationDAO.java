//$Id$
package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mapfinder.modal.Location;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class LocationDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
	// ====================== create location ==============================

	public boolean createLocation(Location location) {
		try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_LOCATION);) {
			stmt.setInt(1, location.getMapId());
			int result = stmt.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
//	========================  view locations  =========================================
	public List<Location> viewLocations(){
		List<Location> locations = new ArrayList<>();
		try (Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(QueryUtil.VIEW_LOCATION);
            
            while (rs.next()) {
                locations.add(new Location(rs.getInt("location_id"),rs.getInt("map_id"),rs.getString("location_name"),rs.getString("region_type"),rs.getDate("created_at")));
                
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return locations;
	}
	
	
//	===============================================  delete location ==================================================
	
    public boolean deleteLocation(int locationId) {
    	try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.DELETE_LOCATION)){
    		stmt.setInt(1, locationId);
    		int result = stmt.executeUpdate();	
			return result>0;
		} catch (Exception e) {
			return false;
			
		}
    }
}
