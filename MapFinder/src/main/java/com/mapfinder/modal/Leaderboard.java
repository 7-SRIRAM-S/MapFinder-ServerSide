package com.mapfinder.modal;

public class Leaderboard {

	private int leaderboardId;
	private int userId;
	private int totalScore;
	private int totalGame;
	private double averageScore;
	private String userName;
	private int certificateCount;
	private boolean isFriend;
	private boolean isAlreadyRequested;

	

	public Leaderboard(int leaderboardId, int userId, int totalScore, int totalGame, double averageScore,
			 String userName, int certificateCount, boolean isFriend) {
		this.leaderboardId = leaderboardId;
		this.userId = userId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.userName = userName;
		this.certificateCount = certificateCount;
		this.isFriend = isFriend;
	}
	
	
	public Leaderboard(boolean isFriend,int leaderboardId, int userId, int totalScore, int totalGame, double averageScore,
			 String userName, int certificateCount) {
		this.leaderboardId = leaderboardId;
		this.userId = userId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.userName = userName;
		this.certificateCount = certificateCount;
		this.isAlreadyRequested = isFriend;
	}

	
	
	


	public int getCertificateCount() {
		return certificateCount;
	}

	public void setCerticateCount(int certificate) {
		if(certificate>=0) {
			this.certificateCount = certificate;
		}
	}
	
	public boolean getIsFriend() {
		return isFriend;
	}
	
	public void setIsFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	
	public int getLeaderboardId() {
		return this.leaderboardId;
	}

	public void setLeaderboardId(int leaderboardId) {
		if (leaderboardId >= 0)
			this.leaderboardId = leaderboardId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		if (userId >= 0)
			this.userId = userId;
	}

	

	public int getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(int totalScore) {
		if (totalScore >= 0)
			this.totalScore = totalScore;
	}

	public int getTotalGame() {
		return this.totalGame;
	}

	public void setTotalGame(int totalGame) {
		if (totalGame >= 0)
			this.totalGame = totalGame;
	}

	public double getAverageScore() {
		return this.averageScore;
	}

	public void setAverageScore(double averageScore) {
		if (averageScore >= 0)
			this.averageScore = averageScore;
	}

	

	public void setUserName(String userName) {
		if (userName != null)
			this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isAlreadyRequested() {
		return isAlreadyRequested;
	}

	public void setAlreadyRequested(boolean isAlreadyRequested) {
		this.isAlreadyRequested = isAlreadyRequested;
	}
	
	
	

}
