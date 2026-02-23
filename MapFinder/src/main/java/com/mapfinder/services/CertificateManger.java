package com.mapfinder.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.CertificateDAO;

public class CertificateManger {
	private static CertificateDAO certificate = new CertificateDAO();
	private static final Logger LOGGER=LogManager.getLogger(CertificateManger.class.getName());
	
	
	public static int userCertificateCount(int userId) {
		try {
			return CertificateManger.certificate.userCertificateCount(userId);
			
		} catch (Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return 0;
		}
	}
}
