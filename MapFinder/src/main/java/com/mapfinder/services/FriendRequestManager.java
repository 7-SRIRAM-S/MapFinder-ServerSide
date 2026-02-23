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
    
    

    public static boolean addFriend( int senderId, int receiverId) {
    	FriendRequest frRequest= null;
    	try {
    		frRequest=new FriendRequest( senderId, receiverId);
			return frdRequest.insertFriendRequest(frRequest);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return false;
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
