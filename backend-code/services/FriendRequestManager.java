//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.FriendRequestDAO;
import com.mapfinder.modal.FriendRequest;
import com.mapfinder.modal.Users;

public class FriendRequestManager {
    private static final Logger LOGGER=LogManager.getLogger(FriendRequestManager.class.getName());
    private static FriendRequestDAO frdRequest = new FriendRequestDAO();
    
    

    public static boolean addGame(int requestId, int senderId, int receiverId, String status, Date createdAt) {
    	LOGGER.trace(new StringBuilder("::: Add frdRequest into DB :::  Creating Object for frdRequest ::: ").toString());
    	FriendRequest frRequest= null;
    	try {
    		frRequest=new FriendRequest(requestId, senderId, receiverId, status, createdAt);
			return FriendRequestManager.frdRequest.insertFriendRequest(frRequest);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
    
    public static List<Users> listAllLocations(){
    	LOGGER.trace(new StringBuilder("::: view all friends into DB :::  Creating Object for friends ::: ").toString());
    	List<Users> frdRequest = new ArrayList<>();
    	try {
    		
    		frdRequest = FriendRequestManager.frdRequest.getFriends();
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	
    	return frdRequest;
    	
    }
    
    
    public static boolean removeFrd(int frdId,int userId) {
    	LOGGER.trace(new StringBuilder("::: removeFrd  into DB :::  Creating Object for friends ::: ").toString());
    	try {
			return FriendRequestManager.frdRequest.delete(frdId, userId);	
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
    }
}
