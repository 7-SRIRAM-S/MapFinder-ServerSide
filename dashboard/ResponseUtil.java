package com.mapfinder.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseUtil {
	
	public static JSONObject buildResponse(JSONObject data,String message) {
			
		JSONObject response=new JSONObject();
		
		response.put("data", data);
		response.put("status", "success");
		response.put("message", message);
		
		
		return response;
	}
	
	public static JSONObject buildResponse(JSONArray data,String message) {
		
		JSONObject response=new JSONObject();
		
		response.put("data", data);
		response.put("status", "success");
		response.put("message", message);
		
		
		return response;
	}
	
	public static JSONObject buildResponseError(int httpStatuscode,String message) {
		JSONObject json = new JSONObject();
		
		return json.put("Status_Code", httpStatuscode)
				.put("Status", "failed")
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
