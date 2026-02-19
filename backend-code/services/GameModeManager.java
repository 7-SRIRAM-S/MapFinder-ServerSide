//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.GameModeDAO;
import com.mapfinder.modal.Gamemode;


public class GameModeManager {

    private static final Logger LOGGER=LogManager.getLogger(GameModeManager.class.getName());
    private static GameModeDAO gameMode = new GameModeDAO();
    
    

    public static boolean addGame(int mode_id, String modeName, String description, int timeLimitSeconds, boolean isRanked, Date createdAt) {
    	LOGGER.trace(new StringBuilder("::: Add Gamemode into DB :::  Creating Object for gamemode ::: ").toString());
    	Gamemode gameMode= null;
    	try {
    		gameMode=new Gamemode(mode_id, modeName, description, timeLimitSeconds , isRanked ,createdAt);
			return GameModeManager.gameMode.insertMode(gameMode);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
    
    
    
    public static List<Gamemode> listAllLocations(){
    	LOGGER.trace(new StringBuilder("::: view gamemodes into DB :::  Creating Object for games ::: ").toString());
    	List<Gamemode> gameMode = new ArrayList<>();
    	try {
    		
    		gameMode = GameModeManager.gameMode.viewGameModes();
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	
    	return gameMode;
    	
    }
    
    
    
    
    public static boolean deleteLocations(int location_id) {
    	LOGGER.trace(new StringBuilder("::: delete Gamemodes into DB :::  Creating Object for gameMode ::: ").toString());
    	try {
			if(location_id>0) {
				return GameModeManager.gameMode.deleteLocation(location_id);
			}
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
    	return false;
    }
}
