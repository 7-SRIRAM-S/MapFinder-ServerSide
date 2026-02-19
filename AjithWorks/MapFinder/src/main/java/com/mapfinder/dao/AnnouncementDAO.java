package com.mapfinder.dao;

import com.mapfinder.modal.Announcement;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnnouncementDAO {
	private Connection conn = DBUtil.getInstance().getConnection();
    private static final Logger LOGGER=LogManager.getLogger(AnnouncementDAO.class.getName());
    
    public boolean insertAnnouncement(Announcement announcement) {

        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_ANNOUNCEMENT)) {

            stmt.setString(1, announcement.getTitle());
            stmt.setString(2, announcement.getMsg());
            stmt.setInt(3, announcement.getCreatedBy());
            stmt.setBoolean(4, announcement.getIsActive());

            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}
    }

    public List<Announcement> findActive() {
        List<Announcement> list = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(QueryUtil.VIEW_ANNOUNCEMENT);

            while (rs.next()) {
                list.add(new Announcement(
                        rs.getInt("announcement_id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getInt("created_by"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        	
		}
        return list;
    }
}
