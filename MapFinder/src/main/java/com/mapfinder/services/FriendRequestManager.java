//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.mapfinder.dao.FriendRequestDAO;
import com.mapfinder.modal.Challenge;
import com.mapfinder.modal.FriendRequest;
import com.mapfinder.modal.Users;
import com.mapfinder.utils.JSONUtil;

public class FriendRequestManager {
    private static final Logger LOGGER=LogManager.getLogger(FriendRequestManager.class.getName());
    private static FriendRequestDAO frdRequest = new FriendRequestDAO();
    
    public static JSONArray getFriendRequests(int userId) {
    	return JSONUtil.convertFriendRequestIntoJson(frdRequest.getFriendRequests(userId));
    }

    public static boolean acceptFriendRequest(int userId,int friendId) {
    	return frdRequest.acceptFriendRequest(userId, friendId);
    }
    
    public static boolean rejectFriendRequest(int userId,int friendId) {
    	return frdRequest.rejectFriendRequest(userId, friendId);
    }
    
    public static boolean addFriend( int senderId, int receiverId) {
    	FriendRequest frRequest= null;
    	try {
    		frRequest=new FriendRequest( senderId, receiverId);
			return frdRequest.insertFriendRequest(frRequest);
//					&&pushFriendRequestNotification(frRequest);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
    
    public static  boolean pushFriendRequestNotification(FriendRequest frdRequest) {
    	return NotificationManager.addNotification(
				    			frdRequest.getReceiverId(), 
				    			frdRequest.getSenderId(), 
				    			"FRIEND_REQUEST", 
				    			"New Friend Request for you !", 
				    			false);
    }
    
    
    
    
    
    public static boolean isFriend(int userId , int friendId) {
    	try {
    		return  FriendRequestManager.frdRequest.isFriend(userId, friendId);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
    
    public static boolean isAlreadyRequested(int userId,int friendId) {
    	
    	return frdRequest.isAlreadyRequestedDAO(userId,friendId);
    }
    
    
    public static boolean removeFrd(int frdId,int userId) {
    	try {
			return FriendRequestManager.frdRequest.delete(frdId, userId);	
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
}
