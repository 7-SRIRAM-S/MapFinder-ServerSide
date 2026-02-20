package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.modal.User;
import com.mapfinder.utils.DBUtil;

import  com.mapfinder.utils.QueryUtil;

public class UserDAOImpl implements UserDAO{

	private static Connection con=DBUtil.getInstance().getConnection();
    private static final Logger LOGGER=LogManager.getLogger(UserDAOImpl.class.getName());

	
	public long insertUser(User user) {
		try  {
			PreparedStatement ps=con.prepareStatement(QueryUtil.INSERT_USER,Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, "mykey");

			int rowCount=ps.executeUpdate();
			if(rowCount>0) {
				ResultSet rs=ps.getGeneratedKeys();
				while(rs.next()) {
					user.setUserId(rs.getInt(1));
					LOGGER.info(new StringBuilder("New User Inserted into DB ::: User Id => "+user.getUserId()+"   :::").toString());
				}
			}
			return user.getUserId();
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Insert User into DB ::: "+e.getMessage()+"   :::").toString());
		}
		return -1L;
	}

	public  boolean updateUser(User user) {
		return false;
	}

	public  boolean removeUser(User user) {
		return false;
	}

	public  User getUser(int userId) {
		return null;
	}

	public boolean isUserExist(String username, String password) {
		try   {
			PreparedStatement ps=con.prepareStatement(QueryUtil.VIEW_USER);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, "mykey");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());

		}
		return false;
	}

	public boolean isUserNameExist(String username) {
		try {
			PreparedStatement ps=con.prepareStatement(QueryUtil.CHECK_USERNAME);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
		}
		return false;
	}

	public int getUserIdByName(String username) {
		try {
			PreparedStatement ps=con.prepareStatement(QueryUtil.GET_USERID_BYNAME);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
		}
		return -1;
	}

	public long addHint(int userId) {
		try {
			PreparedStatement ps=con.prepareStatement(QueryUtil.ADD_HINT);
			ps.setInt(1, userId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("HINTS");
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
		}
		return -1L;
	}
	
	public long getHint(int userId) {
		try {
			PreparedStatement ps=con.prepareStatement(QueryUtil.SELECT_HINT);
			ps.setInt(1, userId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("HINTS");
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
		}
		return -1L;
	}

	public String getUsernameById(int userId) {
		try {
			PreparedStatement ps=con.prepareStatement(QueryUtil.SELECT_USERNAME);
			ps.setInt(1, userId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return rs.getString("USERNAME");
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
		}
		return null;
	}



}






//package com.mapfinder.dao;



//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.mapfinder.controller.SignUpServlet;
//import com.mapfinder.modal.User;
//import com.mapfinder.utils.DBUtil;
//
//import  com.mapfinder.utils.QueryUtil;
//
//public class UserDAOImpl implements UserDAO{
//
//	private static Connection con=DBUtil.getInstance().getConnection();
//    private static final Logger LOGGER=LogManager.getLogger(UserDAOImpl.class.getName());
//
//	
//	public boolean insertUser(User user) {
//		try  {
//			PreparedStatement ps=con.prepareStatement(QueryUtil.INSERT_USER,Statement.RETURN_GENERATED_KEYS);
//			
//			ps.setString(1, user.getUsername());
//			ps.setString(2, user.getPassword());
//			ps.setString(3, "mykey");
//
//			int rowCount=ps.executeUpdate();
//			if(rowCount>0) {
//				ResultSet rs=ps.getGeneratedKeys();
//				while(rs.next()) {
//					user.setUserId(rs.getInt(1));
//					LOGGER.info(new StringBuilder("New User Inserted into DB ::: User Id => "+user.getUserId()+"   :::").toString());
//				}
//				return true;
//			}
//		} catch (SQLException e) {
//			LOGGER.error(new StringBuilder("Problem in Insert User into DB ::: "+e.getMessage()+"   :::").toString());
//		}
//		return false;
//	}
//
//	public  boolean updateUser(User user) {
//		return false;
//	}
//
//	public  boolean removeUser(User user) {
//		return false;
//	}
//
//	public  User getUser(int userId) {
//		return null;
//	}
//
//	public boolean isUserExist(String username, String password) {
//		try   {
//			PreparedStatement ps=con.prepareStatement(QueryUtil.VIEW_USER);
//			ps.setString(1, username);
//			ps.setString(2, password);
//			ps.setString(3, "mykey");
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				return true;
//			}
//		} catch (SQLException e) {
//			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
//
//		}
//		return false;
//	}
//
//	public boolean isUserNameExist(String username) {
//		try {
//			PreparedStatement ps=con.prepareStatement(QueryUtil.CHECK_USERNAME);
//			ps.setString(1, username);
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				return true;
//			}
//		} catch (SQLException e) {
//			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
//		}
//		return false;
//	}
//	
//	
////	---
//	
//	public long getHint(int userId) {
//		try {
//			PreparedStatement ps=con.prepareStatement(QueryUtil.SELECT_HINT);
//			ps.setInt(1, userId);
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				return rs.getInt("HINTS");
//			}
//		} catch (SQLException e) {
//			LOGGER.error(new StringBuilder("Problem in Fetch User :::  "+e.getMessage()+"   :::").toString());
//		}
//		return -1L;
//	}
//
//}
