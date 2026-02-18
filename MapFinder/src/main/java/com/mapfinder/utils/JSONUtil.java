package com.mapfinder.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

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
}
