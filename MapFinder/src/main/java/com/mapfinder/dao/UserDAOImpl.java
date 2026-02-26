package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public long getUserIdByName(String username) {
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
		return -1L;
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
			LOGGER.error(new StringBuilder("Problem in Fetch User Hints Count :::  "+e.getMessage()+"   :::").toString());
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
			LOGGER.error(new StringBuilder("Problem in Fetch UserName By ID :::  "+e.getMessage()+"   :::").toString());
		}
		return null;
	}

	public boolean increaseHint(int attemptId) {
		    String sql = "UPDATE users u " +
		                 "JOIN attempts a ON u.user_id = a.user_id " +
		                 "SET u.HINTS = u.HINTS + FLOOR(a.score / 50) " +
		                 "WHERE a.attempt_id = ?";

		    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		        stmt.setInt(1, attemptId);
		        int rows = stmt.executeUpdate();
		        return rows > 0;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		
	}

	public boolean updateHint(int hints,int userId) {
		String sql = "UPDATE users  " +
                "SET HINTS = ? " +
                "WHERE user_id = ?";

	   try (PreparedStatement stmt = con.prepareStatement(sql)) {
		   stmt.setInt(1, hints);
	       stmt.setInt(2, userId);
	       int rows = stmt.executeUpdate();
	       return rows > 0;
	   } catch (Exception e) {
	       e.printStackTrace();
	       return false;
	   }
			
		}

	public boolean makeActive(int userId) {
		String sql = "update users set is_active=1 where user_id=?" ;

		   try (PreparedStatement stmt = con.prepareStatement(sql)) {
		       stmt.setInt(1, userId);
		       int rows = stmt.executeUpdate();
		       return rows > 0;
		   } catch (Exception e) {
		       e.printStackTrace();
		       return false;
		   }
	}
	
	public boolean makeDeactive(int userId) {
		String sql = "update users set is_active=0 where user_id=?" ;
		   try (PreparedStatement stmt = con.prepareStatement(sql)) {
		       stmt.setInt(1, userId);
		       int rows = stmt.executeUpdate();
		       return rows > 0;
		   } catch (Exception e) {
		       e.printStackTrace();
		       return false;
		   }
	}

	public boolean isUserActive(int userId) {
			String sql = "select * from users where user_id=? and is_active=1" ;
		   try (PreparedStatement stmt = con.prepareStatement(sql)) {
		       stmt.setInt(1, userId);
		       	ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					return true;
				}
		   } catch (Exception e) {
		       e.printStackTrace();
		   }
		   
	       return false;

	}

}
