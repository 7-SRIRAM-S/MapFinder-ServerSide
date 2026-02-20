package com.mapfinder.modal;

import java.util.Date;

public class FriendRequest {
	private int requestId;
	private int senderId;
	private int receiverId;
	private String status;
	private Date createdAt;

	public FriendRequest() {
	}

	public FriendRequest(int requestId, int senderId, int receiverId, String status, Date createdAt) {
		this.requestId = requestId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
		this.createdAt = createdAt;
	}
	
	public FriendRequest( int senderId, int receiverId, String status, Date createdAt) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
		this.createdAt = createdAt;
	}

	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(int requestId) {
		if (requestId >= 0)
			this.requestId = requestId;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		if (senderId >= 0)
			this.senderId = senderId;
	}

	public int getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(int receiverId) {
		if (receiverId >= 0)
			this.receiverId = receiverId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		if (status != null || !status.isEmpty())
			this.status = status;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		if (createdAt != null)
			this.createdAt = createdAt;
	}

}
