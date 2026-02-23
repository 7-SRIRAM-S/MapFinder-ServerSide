package com.mapfinder.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;


public class DashBoardManager {
	
    private static final Logger LOGGER=LogManager.getLogger(DashBoardManager.class.getName());

	public static int getHints(int user_id) {
		
		
		return (int) UserManager.getHint(user_id);
	}
	
	public static int getPoint(int user_id) {

		return LeaderBoardManager.getTotalScore(user_id);
	}
	
	public static int getCertificateCount(int user_id) {
		

		return CertificateManger.userCertificateCount(user_id);
	}
	
	public static JSONArray getTopPlayers(int userId) {
		

		return LeaderBoardManager.topFiveLeaderBoard(userId);
	}
	
	public static JSONArray getAnnouncent(int client_id) {
		

		
		return AnnouncementManager.getActiveAnnouncement(client_id);
	}

}
