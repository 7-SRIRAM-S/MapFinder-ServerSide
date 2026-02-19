package com.mapfinder.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DBUtil {
	public Connection con;
	public static DBUtil instance;
    private static final Logger LOGGER=LogManager.getLogger(DBUtil.class.getName());

	
	private DBUtil() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mapfinder","root","@jith07");
		} catch (SQLException e) {
			LOGGER.fatal(new StringBuilder("SQL Exception ::: "+e.getMessage()+" ::: ").toString());
		} catch (ClassNotFoundException e) {
			LOGGER.fatal(new StringBuilder("ClassNotFound Exception ::: "+e.getMessage()+" ::: ").toString());
		}
		catch(Exception e) {
			LOGGER.fatal(new StringBuilder("DBConnection Exception ::: "+e.getMessage()+" ::: ").toString());
		}
	}
	
	public static DBUtil getInstance() {
		if(instance==null) {
			try {
				instance=new DBUtil();
			} catch (Exception e) {
				LOGGER.fatal(new StringBuilder("DBConnection Exception ::: "+e.getMessage()+" ::: ").toString());
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		return con;
	}
}