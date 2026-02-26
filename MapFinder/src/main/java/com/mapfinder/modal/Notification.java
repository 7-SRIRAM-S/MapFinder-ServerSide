package com.mapfinder.modal;

import java.util.Date;

public class Notification {
	private int notificationId;
	private int userId;
	private int senderId;
	private String type;
	private String message;
	private boolean isRead;
	private Date createdAt;
	private String senderName;
	
	
	
	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public Notification(int userId, int senderId, String type, String message, boolean isRead) {
		super();
		this.userId = userId;
		this.senderId = senderId;
		this.type = type;
		this.message = message;
		this.isRead = isRead;
	}
	
	
	public Notification(String senderName, String type, String message) {
		this.senderName=senderName;
		this.type = type;
		this.message = message;
	}
	
	
	public Notification(int notificationId, int userId, int senderId, String type, String message, boolean isRead,
			Date createdAt) {
		super();
		this.notificationId = notificationId;
		this.userId = userId;
		this.senderId = senderId;
		this.type = type;
		this.message = message;
		this.isRead = isRead;
		this.createdAt = createdAt;
	}
	
	
	
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
