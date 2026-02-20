package com.mapfinder.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;


public class DashBoardManager {
	
    private static final Logger LOGGER=LogManager.getLogger(DashBoardManager.class.getName());

	public static int getHints(int user_id) {
		
		LOGGER.trace(new StringBuilder("going to get hint value from UserManager").toString());
		
		return (int) UserManager.getHint(user_id);
	}
	
	public static int getPoint(int user_id) {
		LOGGER.trace(new StringBuilder("going to get total score from LeaderBoardManager").toString());

		return LeaderBoardManager.getTotalScore(user_id);
	}
	
	public static int getCertificateCount(int user_id) {
		
		LOGGER.trace(new StringBuilder("going to get certificate count from CertificateManager").toString());

		return CertificateManger.userCertificateCount(user_id);
	}
	
	public static JSONArray getTopPlayers(int userId) {
		
		LOGGER.trace(new StringBuilder("going to get top players (5) from LeaderBoardManager").toString());

		return LeaderBoardManager.topFiveLeaderBoard(userId);
	}
	
	public static JSONArray getAnnouncent(int client_id) {
		
		LOGGER.trace(new StringBuilder("going to get Announcements from AnnouncementManager").toString());

		
		return AnnouncementManager.getActiveAnnouncement(client_id);
	}

}
