//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.mapfinder.dao.ErrorStatesDAO;
import com.mapfinder.modal.ErrorState;

public class ErrorStateManager {
	private static ErrorStatesDAO  errorState = new ErrorStatesDAO();
	private static final Logger LOGGER=LogManager.getLogger(ErrorStatesDAO.class.getName());
	
	public static boolean addErrorState(int id, int userId, int modeId, int stateId, String wrongAnswer, String correctAnswer, int attemptNumber, Date createdAt, Date updateAt) {
		LOGGER.trace(new StringBuilder("::: Add ErrorState into DB :::  Creating Object for ErrorState ::: ").toString());
		ErrorState errorState = null;
		
		try {
			
			return ErrorStateManager.errorState.insertErrorState(errorState);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return false;
	}
	
	public static List<ErrorState> getErrorStateById(int userId){
		List<ErrorState> errorState = new ArrayList<ErrorState>();
		try {
			errorState = ErrorStateManager.errorState.findByUser(userId);
			
		} catch (Exception e) {

			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return errorState;
	}
}
