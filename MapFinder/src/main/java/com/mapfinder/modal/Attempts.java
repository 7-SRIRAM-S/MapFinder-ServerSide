package com.mapfinder.modal;

import java.util.Date;

public class Attempts {
	private int attemptId;
	private int userId;
	private int mapId;
	private int modeId;
	private int score;
	private int totalScore;
	private int correctAnswer;
	private int wrongAnswer;
	private Date startedAt;
	private Date endedAt;
	private int durationSeconds;

	public Attempts() {

	}

	public Attempts(int attemptId, int userId, int mapId, int modeId, int score, int totalScore, int correctAnswer, int wrongAnswer, Date startedAt, Date endedAt, int durationSeconds) {
		this.attemptId = attemptId;
		this.userId = userId;
		this.mapId = mapId;
		this.modeId = modeId;
		this.score = score;
		this.totalScore = totalScore;
		this.correctAnswer = correctAnswer;
		this.wrongAnswer = wrongAnswer;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
		this.durationSeconds = durationSeconds;
	}


	public Attempts( int userId, int modeId, int score, int totalScore, int correctAnswer, int wrongAnswer, Date startedAt, Date endedAt, int durationSeconds) {
		this.userId = userId;
		
		this.modeId = modeId;
		this.score = score;
		this.totalScore = totalScore;
		this.correctAnswer = correctAnswer;
		this.wrongAnswer = wrongAnswer;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
		this.durationSeconds = durationSeconds;
	}
	
	public Attempts(int userId,int modeId) {
		this.userId=userId;
		this.modeId=modeId;
	}
	
	public int getAttemptId() {

		return this.attemptId;
	}

	public void setAttemptId(int attemptId) {
		if (attemptId >= 0)
			this.attemptId = attemptId;

	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		if (userId >= 0)
			this.userId = userId;
	}

	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		if (mapId >= 0)
			this.mapId = mapId;
	}

	public int getModeId() {
		return this.modeId;
	}

	public void setModeId(int modeId) {
		if (modeId >= 0)
			this.modeId = modeId;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		if (score >= 0)
			this.score = score;
	}

	public int getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(int totalScore) {
		if (totalScore >= 0)
			this.totalScore = totalScore;
	}

	public int getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		if (correctAnswer >= 0)
			this.correctAnswer = correctAnswer;
	}

	public int getWrongAnswer() {
		return this.wrongAnswer;
	}

	public void setWrongAnswer(int wrongAnswer) {
		if (wrongAnswer >= 0)
			this.wrongAnswer = wrongAnswer;
	}

	public Date getStartedAt() {
		return this.startedAt;
	}

	public void setStartedAt(Date startedAt) {
		if (startedAt != null)
			this.startedAt = startedAt;
	}

	public Date getEndedAt() {

		return this.endedAt;
	}

	public void setEndedAt(Date endedAt) {
		if (endedAt != null)
			this.endedAt = endedAt;
	}

	public int getDurationSeconds() {
		return this.durationSeconds;
	}

	public void setDurationSeconds(int durationSeconds) {
		if (durationSeconds > 0)
			this.durationSeconds = durationSeconds;
	}

}
