//$Id$
package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mapfinder.modal.FriendRequest;
import com.mapfinder.modal.User;
import com.mapfinder.modal.Users;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class FriendRequestDAO {
	private static Connection conn = DBUtil.getInstance().getConnection();
	
	public boolean insertFriendRequest(FriendRequest friendRequest) {
        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_FRIEND_REQUEST)) {

               stmt.setInt(1, friendRequest.getReceiverId());
               stmt.setInt(2, friendRequest.getSenderId());
               
               stmt.executeUpdate();
               return true;
         }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	public List<FriendRequest> getFriendRequests(int userId){
		List<FriendRequest> friendRequests=new ArrayList<>();
		try(PreparedStatement stmt = conn.prepareStatement(QueryUtil.GET_FRIENDREQUEST)){
    		stmt.setInt(1, userId);
    		ResultSet rs= stmt.executeQuery();
    		while(rs.next()) {
//    			System.out.println(rs.getInt("sender_id"));
    			friendRequests.add(new FriendRequest(rs.getString("USERNAME"),rs.getInt("sender_id")));
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}

		return friendRequests;
	}
	
	public boolean acceptFriendRequest(int userId,int friendId) {
		try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.ACCEPT_FRIENDREQUEST)) {

			stmt.setInt(1, userId);
    		stmt.setInt(2, friendId);
    		stmt.setInt(3, friendId);
    		stmt.setInt(4, userId);
            
            int rows=stmt.executeUpdate();
            if(rows>0) {
            	return makeFriends(userId,friendId);
            }
      }
     catch(Exception e) {
     	e.printStackTrace();
     }
     	return false;

	}
	
	public static boolean makeFriends(int userId,int friendId) {
		try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.MAKE_FRIENDS)) {

			stmt.setInt(1, userId);
    		stmt.setInt(2, friendId);

            
            int rows=stmt.executeUpdate();
            return rows>0;
      }
     catch(Exception e) {
     	e.printStackTrace();
     	return false;
     }
	}
	
	
	public boolean rejectFriendRequest(int userId,int friendId) {
		try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.REJECT_FRIENDREQUEST)) {

				stmt.setInt(1, userId);
	    		stmt.setInt(2, friendId);
	    		stmt.setInt(3, friendId);
	    		stmt.setInt(4, userId);
	            
	            int rows=stmt.executeUpdate();
	            return rows>0;
	      }
	     catch(Exception e) {
	     	e.printStackTrace();
	     	return false;
	     }
	}
	
	
	
    public boolean delete(int friend_id , int user_id){
        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.REMOVE_FRIEND)) {

            stmt.setInt(1, friend_id);
            stmt.setInt(2, user_id);
            int result=stmt.executeUpdate();
            return result>0;
        }
        catch (Exception e) {
        	return false;
		}
    }
    
    
    public boolean isFriend(int userId , int friendId) {
    	try(PreparedStatement stmt = conn.prepareStatement(QueryUtil.IS_FRIEND)){
    		stmt.setInt(1, userId);
    		stmt.setInt(2, friendId);
    		stmt.setInt(3, friendId);
    		stmt.setInt(4, userId);
    		ResultSet rs= stmt.executeQuery();
    		while(rs.next()) {
    			return true;
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }

	public boolean isAlreadyRequestedDAO(int userId, int friendId) {
		try(PreparedStatement stmt = conn.prepareStatement(QueryUtil.IS_ALREADY_REQUESTED)){
    		stmt.setInt(1, userId);
    		stmt.setInt(2, friendId);
    		stmt.setInt(3, friendId);
    		stmt.setInt(4, userId);
    		ResultSet rs= stmt.executeQuery();
    		while(rs.next()) {
//    			System.out.println(userId+"|"+friendId);

    			return true;
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
	}

	

}


