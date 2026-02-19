//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.MapDAO;
import com.mapfinder.modal.Map;

public class MapManager {
	private static MapDAO map = new MapDAO();
    private static final Logger LOGGER=LogManager.getLogger(MapManager.class.getName());
    
    public static boolean addMap(int mapId, String mapName, String mapType, boolean isActive, Date created_at) {
    	LOGGER.trace(new StringBuilder("::: Add map into DB :::  Creating Object for map ::: ").toString());
    	Map map = null;
    	try {
			map = new Map(mapId, mapName, mapType, isActive, created_at);
			return MapManager.map.addMap(map);
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return false;
		}
    	
    }
    
    
    public static List<Map> getAllMaps(){
    	LOGGER.trace(new StringBuilder("::: View map into DB :::  Creating Object for list map ::: ").toString());
    	List<Map> maps = new ArrayList<>();
    	try {
			maps = MapManager.map.findAll();
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return  maps;
    }
    
    

}
