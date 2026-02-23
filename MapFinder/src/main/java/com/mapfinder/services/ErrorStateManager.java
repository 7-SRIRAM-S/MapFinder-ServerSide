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
	private static ErrorStatesDAO  errorStateDAO = new ErrorStatesDAO();
	private static final Logger LOGGER=LogManager.getLogger(ErrorStatesDAO.class.getName());
	
	
	public static List<ErrorState> getErrorStateById(int userId){
		List<ErrorState> errorState = new ArrayList<ErrorState>();
		try {
			errorState = ErrorStateManager.errorStateDAO.findByUser(userId);
			
		} catch (Exception e) {

			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return errorState;
	}

	public static boolean addErrorState(String correctState, String wrongState, long userId, int attemptId, int modeId) {
		ErrorState errorState=new ErrorState(correctState, wrongState,userId, attemptId, modeId);
		return errorStateDAO.insertErrorState(errorState);
	}
}
