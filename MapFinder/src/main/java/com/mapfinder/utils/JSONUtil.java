package com.mapfinder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.Announcement;
import com.mapfinder.modal.Challenge;
import com.mapfinder.modal.ErrorState;
import com.mapfinder.modal.FriendRequest;
import com.mapfinder.modal.Leaderboard;
import com.mapfinder.modal.Notification;



public class JSONUtil {
	public static JSONObject readAsJSON(HttpServletRequest request) {
		
		LogManager.getLogger(JSONUtil.class).trace(new StringBuilder("::: Reading json data from servlet ::: Utility ::: returning json data :::").toString());

		StringBuilder sb=new StringBuilder();
		BufferedReader reader=null;
		try {
			reader = request.getReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String line=null;
		try {
			while((line=reader.readLine())!=null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json=new JSONObject(sb.toString());
		return json;
	}
	
	
	
	public static JSONArray convertLeaderBoardToJson(List<Leaderboard> leaderboards) {
		JSONArray mapList = new JSONArray();
		for(Leaderboard leader: leaderboards) {
			JSONObject obj=new JSONObject();

			obj.put("totalScore", leader.getTotalScore());
			obj.put("totalGame", leader.getTotalGame());
			obj.put("averageScore", leader.getAverageScore());
			obj.put("userName", leader.getUserName());
			obj.put("totalCertificate" , leader.getCertificateCount());
			obj.put("isFriend", leader.getIsFriend());
			obj.put("isAlreadyRequested",leader.isAlreadyRequested());
			mapList.put(obj);
		}
		return mapList;
	}
	
	
	
	public static JSONArray convertAnnouncementTOJson(List<Announcement> announcements) {
		JSONArray announcementList = new JSONArray();
		
		for(Announcement announcement: announcements) {
			JSONObject obj = new JSONObject();
			obj.put("announcementId", announcement.getAnnouncementsId());
			obj.put("title", announcement.getTitle());
			obj.put("message", announcement.getMsg());
			obj.put("createdBy", announcement.getCreatedBy());
			obj.put("isActive", announcement.getIsActive());
			obj.put("createdAt", announcement.getCreatedAt());
			announcementList.put(obj);
		}
		
		return announcementList;
	}
	
	 public static JSONArray convertErrorStateToJson(List<ErrorState> errorStates) {
	        JSONArray errorStateList = new JSONArray();

	        for (ErrorState es : errorStates) {
	            JSONObject obj = new JSONObject();
	            obj.put("wrongAnswer", es.getWrongAnswer());
	            
	            errorStateList.put(obj);
	        }

	        return errorStateList;
	 }
	 
	 
	 public static JSONArray convertNotificationsToJson(List<Notification> notifications){
			JSONArray notificationJson = new JSONArray();
			
			for(Notification notification: notifications) {
				JSONObject obj = new JSONObject();
				obj.put("senderName", notification.getSenderName());
				obj.put("message", notification.getSenderName() +notification.getMessage());
				obj.put("type", notification.getType());
				notificationJson.put(obj);
				
			}
			return notificationJson;
		}
		
		
		public static JSONArray convertChallengeTOJson(List<Challenge> challenges) {
			JSONArray challengesJson = new JSONArray();
			for(Challenge challenge : challenges) {
				JSONObject obj = new JSONObject();
				obj.put("challengeId", challenge.getChallengeId());
				obj.put("challengerId",challenge.getChallengerId());
				obj.put("opponentId", challenge.getOpponentId());
				obj.put("mode", challenge.getMode());
				obj.put("status", challenge.getStatus());
				obj.put("challengerScore", challenge.getChallengerScore());
				obj.put("opponentScore", challenge.getOpponentScore());
				obj.put("winnerId", challenge.getWinnerId());
				challengesJson.put(obj);
			}
			return challengesJson;
		}



		public static JSONArray convertFriendRequestIntoJson(
				List<FriendRequest> friendRequests) {
			JSONArray friendRequestsJson=new JSONArray();
			for(FriendRequest fr:friendRequests) {
				JSONObject obj=new JSONObject();
				obj.put("username", fr.getUsername());
				obj.put("senderId", fr.getSenderId());
				obj.put("type", "friend_request");
				obj.put("message", fr.getUsername()+" want to friend with you");
				friendRequestsJson.put(obj);
			}
			
			return friendRequestsJson;
		}
		
		public static JSONArray combineNotifications(JSONArray notificationJson,JSONArray friendRequestJson) {

		    JSONArray finalArray = new JSONArray();

		    for (int i = 0; i < notificationJson.length(); i++) {
		        finalArray.put(notificationJson.getJSONObject(i));
		    }

		    for (int i = 0; i < friendRequestJson.length(); i++) {
		        finalArray.put(friendRequestJson.getJSONObject(i));
		    }

		    return finalArray;
		}
		
	
	
	
	
	
}