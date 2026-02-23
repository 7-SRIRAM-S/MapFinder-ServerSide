//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.mapfinder.dao.GameModeDAO;
import com.mapfinder.modal.Gamemode;


public class GameModeManager {

    private static final Logger LOGGER=LogManager.getLogger(GameModeManager.class.getName());
    private static GameModeDAO gameMode = new GameModeDAO();
    
    
    public static JSONObject getGameDetails(int userId) {
    	long hints=UserManager.getHint(userId);
    	JSONObject json=new JSONObject();
    	json.put("hintsCount", hints);
    	return json;
    }
    
    public static int getGameModeIdByAttemptId(int attemptId) {
    	return gameMode.getGameModeIdByAttemptId(attemptId);
    }
    
    public static int getGameModeId(String mode) {

        if (mode == null) return -1;

        switch (mode.toLowerCase().trim()) {

            case "learn mode":
            case "learn-mode":
                return 1;

            case "self evaluate mode":
            case "self-evaluate-mode":
                return 2;

            case "rectify mode":
            case "rectify-mode":
                return 3;

            case "write mode":
            case "write-mode":
                return 4;

            case "bot challenge mode":
            case "bot-challenge-mode":
                return 5;

            case "quiz mode":
            case "quiz-mode":
                return 6;

            default:
                return -1;
        }
    }

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
