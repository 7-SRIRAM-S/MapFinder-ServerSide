package com.mapfinder.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.mapfinder.dao.ChallengeDAO;
import com.mapfinder.modal.Challenge;
import com.mapfinder.utils.JSONUtil;

public class ChallengeManager {

	private static ChallengeDAO challengeDao = new ChallengeDAO();
    private static final Logger LOGGER=LogManager.getLogger(ChallengeManager.class.getName());
    
    
    public static boolean makeChallenge(int challengerId, int opponentId) {
    	Challenge challenge = null;
    	try {
			challenge = new Challenge(challengerId, opponentId,  "PENDING", 0, 0);
			return challengeDao.makeChallenge(challenge)&&pushChallengeNotification(challenge);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return false;
			
		}
    }
    
    
    public static JSONArray viewChallenges(int userId) {
    	List<Challenge> challenges  = new ArrayList<>();
    	try {
			challenges =  challengeDao.viewChallenges(userId);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());}
    	
    	return JSONUtil.convertChallengeTOJson(challenges);
    }
    
    public static  boolean pushChallengeNotification(Challenge challenge) {
    	return NotificationManager.addNotification(
				    			challenge.getChallengerId(), 
				    			challenge.getOpponentId(), 
				    			"CHALLENGE_SENT", 
				    			" tried to challenge you !", 
				    			false);
    }
    
}
