//$Id$
package com.mapfinder.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.mapfinder.dao.AnnouncementDAO;
import com.mapfinder.modal.Announcement;
import com.mapfinder.utils.JSONUtil;

public class AnnouncementManager {
	private static AnnouncementDAO  announcement = new AnnouncementDAO();
	private static final Logger LOGGER=LogManager.getLogger(AnnouncementManager.class.getName());
	
	public static boolean addAnnouncement( String title, String msg, int createdBy, boolean isActive) {
		LOGGER.trace(new StringBuilder("::: Add Announcement into DB :::  Creating Object for Announcement ::: ").toString());
		Announcement announcement = null;
		
		try {
			announcement= new Announcement(title, msg, createdBy, isActive);
			
			return AnnouncementManager.announcement.insertAnnouncement(announcement);
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return false;
	}
	
	public static JSONArray getActiveAnnouncement(int userId){
		LOGGER.trace(new StringBuilder("::: view Announcement into DB :::  Creating Object for Announcement ::: ").toString());
		List<Announcement> announcement = new ArrayList<Announcement>();
		try {
			announcement = AnnouncementManager.announcement.findActive(userId);
			
		} catch (Exception e) {

			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			
		}
		return JSONUtil.convertAnnouncementTOJson(announcement);
	}
}
