package com.mapfinder.modal;

import java.util.Date;

public class ErrorState {
	private int id;
	private int userId;
	private int modeId;
	private int stateId;
	private String wrongAnswer;
	private String correctAnswer;
	private int attemptNumber;
	private Date createdAt;
	private Date updateAt;

	public ErrorState() {
	}

	public ErrorState(int id, int userId, int modeId, int stateId, String wrongAnswer, String correctAnswer, int attemptNumber, Date createdAt, Date updateAt) {
		this.id = id;
		this.userId = userId;
		this.modeId = modeId;
		this.stateId = stateId;
		this.wrongAnswer = wrongAnswer;
		this.correctAnswer = correctAnswer;
		this.attemptNumber = attemptNumber;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	
	
	public ErrorState( int userId, int modeId, int stateId, String wrongAnswer, String correctAnswer, int attemptNumber, Date createdAt, Date updateAt) {
		
		this.userId = userId;
		this.modeId = modeId;
		this.stateId = stateId;
		this.wrongAnswer = wrongAnswer;
		this.correctAnswer = correctAnswer;
		this.attemptNumber = attemptNumber;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id >= 0)
			this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		if (userId >= 0)
			this.userId = userId;
	}

	public int getModeId() {
		return this.modeId;
	}

	public void setModeId(int modeId) {
		if (modeId >= 0)
			this.modeId = modeId;
	}

	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		if (stateId >= 0)
			this.stateId = stateId;
	}

	public String getWrongAnswer() {
		return this.wrongAnswer;
	}

	public void setWrongAnswer(String wrongAnswer) {
		if (wrongAnswer != null || !wrongAnswer.isEmpty())
			this.wrongAnswer = wrongAnswer;
	}

	public String getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		if (correctAnswer != null || !correctAnswer.isEmpty())
			this.correctAnswer = correctAnswer;
	}

	public int getAttemptNumber() {
		return this.attemptNumber;
	}

	public void setAttemptNumber(int attemptNumber) {
		if (attemptNumber >= 0)
			this.attemptNumber = attemptNumber;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		if (createdAt != null)
			this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		if (updateAt != null)
			this.updateAt = updateAt;
	}

}
