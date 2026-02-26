package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.mapfinder.dao.NotificationDAO;
import com.mapfinder.modal.Leaderboard;
import com.mapfinder.modal.Notification;
import com.mapfinder.utils.JSONUtil;

public class NotificationManager {
	private static NotificationDAO notificationDAO=new NotificationDAO();
    private static final Logger LOGGER=LogManager.getLogger(NotificationManager.class.getName());

	public static boolean addNotification(int userId, int senderId, String type, String message, boolean isRead) {
		LOGGER.trace(new StringBuilder("::: Add Notification into DB :::  Creating Object for Notification ::: ").toString());
		Notification notification= null;
		try {
			notification = new Notification(userId, senderId, type, message, isRead);
			 return notificationDAO.insertNotification(notification);
		}
		catch(Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return false;
		}

	}
	
	public static JSONArray viewNotifications(int userId) {
    	LOGGER.trace(new StringBuilder("::: view notifications  :::  Creating Object for notification List ::: ").toString());
    	List<Notification> notifications = new ArrayList<>();
    	try {
    		notifications = notificationDAO.getAllNotification(userId);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
		}
    	return JSONUtil.convertNotificationsToJson(notifications);
	}
}
