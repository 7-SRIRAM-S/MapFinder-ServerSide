package com.mapfinder.services;

import org.json.JSONArray;
import org.json.JSONObject;

public class DashboardManager {
	
	public static int getHints(int user_id) {
		
		return 10;
	}
	
	public static int getPoint(int user_id) {
		
		return 100;
	}
	
	public static int getCertificateCount(int user_id) {
		
		return 10;
	}
	
	public static JSONArray getTopPlayers() {
		JSONArray array=new JSONArray();
		
		JSONObject json=new JSONObject();
		
		json.put("name", "Kowsik");
		json.put("points", 1000);
		json.put("certificate", 10);
		
		array.put(json);
		
		return array;
	}
	
	public static JSONArray getMessage(int client_id,int limit) {
		JSONArray array=new JSONArray();
		
		JSONObject json=new JSONObject();
		
		json.put("message", "10 hints - keep up the good work!");
		json.put("time", "2026-02-17 15:50:00");
		
		array.put(json);
		
		return array;
	}

}
