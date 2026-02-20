package com.mapfinder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.modal.Certificate;
import com.mapfinder.services.AnnouncementManager;
import com.mapfinder.utils.DBUtil;
import com.mapfinder.utils.QueryUtil;

public class CertificateDAO {
	private static Connection conn = DBUtil.getInstance().getConnection();
	private static final Logger LOGGER=LogManager.getLogger(AnnouncementManager.class.getName());
	
	public boolean insertCertificate(Certificate certificate) {
		LOGGER.trace(new StringBuilder("::: Insert CertificateDAO into DB :::  Creating Object for Certificate ::: ").toString());
        try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_CERTIFICATE)) {
        	stmt.setString(1, certificate.getName());
        	stmt.setString(2,certificate.getRating());
        	stmt.setString(3,certificate.getIssued_by());
            stmt.executeUpdate();
            return true;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	
	public boolean insertUserCertificate(int userId, int certificate_id) {
		LOGGER.trace(new StringBuilder("::: Insert insertUserCertificateDAO into DB :::  Creating Object for Certificate ::: ").toString());
		try (PreparedStatement stmt = conn.prepareStatement(QueryUtil.INSERT_INTO_USER_CERTIFICATE)){
        	stmt.setInt(1, userId);
        	stmt.setInt(2, certificate_id);
            stmt.executeUpdate();
            return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public int userCertificateCount(int userId) {
		LOGGER.trace(new StringBuilder("::: get userCertificateCount into DB :::  Creating Object for Certificate ::: ").toString());
	    int totalCertificates = 0;
	    try (PreparedStatement ps = conn.prepareStatement(QueryUtil.GET_CERTIFICATE_USERS)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            totalCertificates = rs.getInt("totalCertificates");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return totalCertificates;
	}
    
	

}
