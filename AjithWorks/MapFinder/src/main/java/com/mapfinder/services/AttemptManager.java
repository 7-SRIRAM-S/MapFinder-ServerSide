//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.AttemptDAO;
import com.mapfinder.modal.Attempts;

public class AttemptManager {
	private static AttemptDAO  attempt= new AttemptDAO();
	private static final Logger LOGGER=LogManager.getLogger(AttemptManager.class.getName());
	
	public static boolean addAttempt(int attemptId, int userId, int mapId, int modeId, int score, int totalScore, int correctAnswer, int wrongAnswer, Date startedAt, Date endedAt, int durationSeconds) {
		LOGGER.trace(new StringBuilder("::: Add Attempts into DB :::  Creating Object for Attempt ::: ").toString());
		Attempts attempt = null;
		
		try {
			attempt = new Attempts(attemptId, userId, mapId, modeId, score, totalScore, correctAnswer, wrongAnswer, startedAt, endedAt, durationSeconds);
			return AttemptManager.attempt.inserAttempt(attempt);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return false;
	}
	
	public static List<Attempts> getAttemptById(int userId){
		List<Attempts> errorState = new ArrayList<Attempts>();
		try {
			errorState = AttemptManager.attempt.findByUserId(userId);
			
		} catch (Exception e) {

			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return errorState;
	}
}
