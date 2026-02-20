package com.mapfinder.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mapfinder.modal.Announcement;
import com.mapfinder.modal.Leaderboard;



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
			obj.put("leaderboardId", leader.getLeaderboardId());
			obj.put("userId", leader.getUserId());
			obj.put("mapId", leader.getMapId());
			obj.put("modeId" , leader.getModeId());
			obj.put("totalScore", leader.getTotalScore());
			obj.put("totalGame", leader.getTotalGame());
			obj.put("averageScore", leader.getAverageScore());
			obj.put("rankPosition", leader.getRankPosition());
			obj.put("userName", leader.getUserName());
			obj.put("totalCertificate" , leader.getCertificateCount());
			obj.put("isFriend", leader.getIsFriend());
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
	
	
	
	
	
}