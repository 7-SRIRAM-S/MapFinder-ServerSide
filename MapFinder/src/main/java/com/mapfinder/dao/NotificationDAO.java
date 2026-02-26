package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.modal.Notification;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class NotificationDAO {
	private static Connection con=DBUtil.getInstance().getConnection();
    private static final Logger LOGGER=LogManager.getLogger(NotificationDAO.class.getName());

	
	public boolean insertNotification(Notification notification) {
		try {
			PreparedStatement stmt  = con.prepareStatement(QueryUtil.INSERT_NOTIFICATION);
			stmt.setInt(1, notification.getUserId() );
			stmt.setInt(2, notification.getSenderId());
			stmt.setString(3, notification.getType());
			stmt.setString(4, notification.getMessage());
			stmt.setBoolean(5, notification.isRead());
			int rowCount = stmt.executeUpdate();
			return rowCount>0;
			
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Insert Notification into DB ::: "+e.getMessage()+"   :::").toString());
			return false;
		}
	}
	
	public List<Notification> getAllNotification(int userId){
		List<Notification> notifications = new ArrayList<>();
		try {
			PreparedStatement stmt = con.prepareStatement(QueryUtil.GET_NOTIFICATIONS);
			stmt.setInt(1, userId);
			ResultSet rs= stmt.executeQuery();
			while(rs.next()) {
				notifications.add(new Notification(rs.getString("USERNAME"),rs.getString("type"),rs.getString("message")));
			}
		} catch (SQLException e) {
			LOGGER.error(new StringBuilder("Problem in Fetch Notification from DB ::: "+e.getMessage()+"   :::").toString());
		}
		return notifications;
	}
}
