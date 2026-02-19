//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.LocationDAO;
import com.mapfinder.modal.Location;

public class LocationManager {
    private static final Logger LOGGER=LogManager.getLogger(LocationManager.class.getName());
    private static LocationDAO location = new LocationDAO();
	
    public static boolean addLocation(int Location_Id, int mapId, String locationName, String regionType, Date CREATED_AT) {
    	LOGGER.trace(new StringBuilder("::: Add Locations into DB :::  Creating Object for Locations ::: ").toString());
    	Location location = null;
    	try {
			location=new Location(Location_Id, mapId, locationName, regionType, CREATED_AT);
			return LocationManager.location.createLocation(location);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
    
    public static List<Location> listAllLocations(){
    	LOGGER.trace(new StringBuilder("::: view Locations into DB :::  Creating Object for Locations ::: ").toString());
    	List<Location> location = new ArrayList<>();
    	try {
    		
    		location = LocationManager.location.viewLocations();
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	
    	return location;
    	
    }
    
    
    public static boolean deleteLocations(int location_id) {
    	LOGGER.trace(new StringBuilder("::: delete Locations into DB :::  Creating Object for Locations ::: ").toString());
    	try {
			if(location_id>0) {
				return LocationManager.location.deleteLocation(location_id);
			}
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
    	return false;
    }
}
