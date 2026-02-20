package com.mapfinder.modal;

public class Leaderboard {

	private int leaderboardId;
	private int userId;
	private int mapId;
	private int modeId;
	private int totalScore;
	private int totalGame;
	private double averageScore;
	private int rankPosition;
	private String userName;
	private int certificateCount;
	private boolean isFriend;

	public Leaderboard() {
	}

	public Leaderboard(int leaderboardId, int userId, int mapId, int modeId, int totalScore, int totalGame,
			double averageScore, int rankPosition) {
		this.leaderboardId = leaderboardId;
		this.userId = userId;
		this.mapId = mapId;
		this.modeId = modeId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.rankPosition = rankPosition;
	}

	public Leaderboard(int userId, int mapId, int modeId, int totalScore, int totalGame, double averageScore,
			int rankPosition) {
		this.userId = userId;
		this.mapId = mapId;
		this.modeId = modeId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.rankPosition = rankPosition;
	}

	public Leaderboard(int leaderboardId, int userId, int mapId, int modeId, int totalScore, int totalGame,
			double averageScore, int rankPosition, String userName , int certificate , boolean isFriends) {
		this.leaderboardId = leaderboardId;
		this.userId = userId;
		this.mapId = mapId;
		this.modeId = modeId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.rankPosition = rankPosition;
		this.userName = userName;
		this.certificateCount = certificate;
		this.isFriend = isFriends;
	}
	
	public Leaderboard(int leaderboardId, int userId, int mapId, int modeId, int totalScore, int totalGame,
			double averageScore, int rankPosition, String userName) {
		this.leaderboardId = leaderboardId;
		this.userId = userId;
		this.mapId = mapId;
		this.modeId = modeId;
		this.totalScore = totalScore;
		this.totalGame = totalGame;
		this.averageScore = averageScore;
		this.rankPosition = rankPosition;
		this.userName = userName;
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

	public int getRankPosition() {
		return this.rankPosition;
	}

	public void setRankPosition(int rankPosition) {
		if (rankPosition >= 0)
			this.rankPosition = rankPosition;
	}

	public void setUserName(String userName) {
		if (userName != null)
			this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
	
	

}
