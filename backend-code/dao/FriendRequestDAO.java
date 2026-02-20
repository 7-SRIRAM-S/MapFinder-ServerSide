//$Id$
package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mapfinder.modal.FriendRequest;
import com.mapfinder.modal.Users;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class FriendRequestDAO {
	private Connection conn = DBUtil.getInstance().getConnection();
	
	public boolean insertFriendRequest(FriendRequest friendRequest) {
        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_FRIEND_REQUEST)) {

               stmt.setInt(1, friendRequest.getRequestId());
               stmt.setInt(2, friendRequest.getSenderId());
               stmt.setInt(3, friendRequest.getReceiverId());
               stmt.setString(4, friendRequest.getStatus());
               
               stmt.executeUpdate();
               return true;
         }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	
	public List<Users> getFriends(int userId){
		List<Users> listUsers = new ArrayList<Users>();
		try(PreparedStatement stmt = conn.prepareStatement(QueryUtil.GET_FRIENDS)){
			stmt.setInt(userId, userId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				listUsers.add(new Users(rs.getInt("USERS_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD") , rs.getString("ROLE"),rs.getDate("CREATED_AT")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return listUsers;
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
}


