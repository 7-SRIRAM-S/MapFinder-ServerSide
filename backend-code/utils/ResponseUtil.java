package com.mapfinder.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseUtil {
	
	public static JSONObject buildResponce(JSONObject data,String message) {
			
		JSONObject res=new JSONObject();
		
		res.put("data", data);
		res.put("status", "Success");
		res.put("message", message);
		
		
		return res;
	}
	
	public static JSONObject buildResponce(JSONArray data,String message) {
		
		JSONObject res=new JSONObject();
		
		res.put("data", data);
		res.put("status", "Success");
		res.put("message", message);
		
		
		return res;
	}
	
	public static JSONObject buildResponceError(int httpStatuscode,String message) {
		JSONObject json = new JSONObject();
		
		return json.put("Status_Code", httpStatuscode)
				.put("Status", "Failed")
				.put("message",message);
	}
	
	public static void ProcessResponse(JSONObject result,HttpServletResponse response) {

        response.setContentType("application/json");
        
        
        try {
			response.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}