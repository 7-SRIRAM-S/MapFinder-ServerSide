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
	private static AttemptDAO  attemptDAO= new AttemptDAO();
	private static final Logger LOGGER=LogManager.getLogger(AttemptManager.class.getName());
	
	public static int addAttempt(int userId, int modeId) {
		Attempts attempt = new Attempts(userId,modeId);
		return attemptDAO.inserAttempt(attempt);
	}
	
	public static List<Attempts> getAttemptById(int userId){
		List<Attempts> errorState = new ArrayList<Attempts>();
		try {
			errorState = AttemptManager.attemptDAO.findByUserId(userId);
			
		} catch (Exception e) {

			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return errorState;
	}
	
	
	public static boolean updateAttempt(int score,int attemptId) {
		return attemptDAO.updateAttempt(score,attemptId)&&attemptDAO.finalizeAttempt(attemptId);
	}
	
	public static boolean finalizeAttempt(int attemptId) {
		return attemptDAO.finalizeAttempt(attemptId);
	}
	
}
