package com.mapfinder.modal;

import java.util.Date;

public class Challenge {
	private int challengeId;
	private int challengerId;
	private int opponentId;
	private String mode;
	private String status;
	private int challengerScore;
	private int opponentScore;
	private int winnerId;
	private Date createdAt;
	private Date updateAt;
	
	
	
	
	public Challenge(int challengerId, int opponentId, String status, int challengerScore,
			int opponentScore) {
		this.challengerId = challengerId;
		this.opponentId = opponentId;
		this.status = status;
		this.challengerScore = challengerScore;
		this.opponentScore = opponentScore;
		this.winnerId = winnerId;
	}




	public Challenge(int challengeId, int challengerId, int opponentId, String mode, String status, int challengerScore,
			int opponentScore, int winnerId, Date createdAt, Date updateAt) {
		this.challengeId = challengeId;
		this.challengerId = challengerId;
		this.opponentId = opponentId;
		this.mode = mode;
		this.status = status;
		this.challengerScore = challengerScore;
		this.opponentScore = opponentScore;
		this.winnerId = winnerId;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}




	public int getChallengeId() {
		return challengeId;
	}




	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}




	public int getChallengerId() {
		return challengerId;
	}




	public void setChallengerId(int challengerId) {
		this.challengerId = challengerId;
	}




	public int getOpponentId() {
		return opponentId;
	}




	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}




	public String getMode() {
		return mode;
	}




	public void setMode(String mode) {
		this.mode = mode;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public int getChallengerScore() {
		return challengerScore;
	}




	public void setChallengerScore(int challengerScore) {
		this.challengerScore = challengerScore;
	}




	public int getOpponentScore() {
		return opponentScore;
	}




	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}




	public int getWinnerId() {
		return winnerId;
	}




	public void setWinnerId(int winnerId) {
		this.winnerId = winnerId;
	}




	public Date getCreatedAt() {
		return createdAt;
	}




	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public Date getUpdateAt() {
		return updateAt;
	}




	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	
	
	
	
	
	
}
