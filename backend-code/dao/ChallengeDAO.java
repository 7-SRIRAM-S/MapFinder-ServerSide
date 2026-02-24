package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.modal.Challenge;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class ChallengeDAO {

	Connection conn = DBUtil.getInstance().getConnection();
	private static final Logger LOGGER=LogManager.getLogger(ChallengeDAO.class.getName());
	public boolean makeChallenge(Challenge challenge) {
		try {
			PreparedStatement stmt = conn.prepareStatement(QueryUtil.GET_CHALLENGES);
			stmt.setInt(1,challenge.getChallengerId());
			stmt.setInt(2, challenge.getOpponentId());
			stmt.setString(3, challenge.getMode());
			stmt.setString(4, challenge.getStatus());
			stmt.setInt(5, challenge.getChallengerScore());
			stmt.setInt(6, challenge.getOpponentScore());
			stmt.setInt(7, challenge.getWinnerId());
			
			int result = stmt.executeUpdate();
			return result>0;
		}
		catch(SQLException e) {
			LOGGER.warn(new StringBuilder("::: Problem in Insert Leaderboard :::  "+e.getMessage()+" ::: ").toString());
			return false;
		}
	}
	
	public List<Challenge> viewChallenges(int userId){
		List<Challenge> challenges = new ArrayList<>();
		
		try {
			PreparedStatement stmt =conn.prepareStatement("");
			stmt.setInt(1, userId);
			ResultSet rs= stmt.executeQuery( );
			while(rs.next()) {
				challenges.add(new Challenge(rs.getInt("challenger_id"),rs.getInt("challenge_id"),rs.getInt("opponent_id"),rs.getString("mode"),rs.getString("status"),rs.getInt("challenger_score"),rs.getInt("opponent_score"),rs.getInt("winner_id"),rs.getDate("created_at"),rs.getDate("updated_at")));
			}
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Insert Leaderboard :::  "+e.getMessage()+" ::: ").toString());
			
		}
		
		return challenges;
		
	}
}
