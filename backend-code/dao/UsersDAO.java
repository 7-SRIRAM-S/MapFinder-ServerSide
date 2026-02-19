//$Id$
package com.mapfinder.dao;

import com.mapfinder.modal.Users;
import com.mapfinder.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
	Connection conn = DBUtil.getInstance().getConnection();
    
    public boolean save(Users user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            stmt.executeUpdate();
            return true;
        }
        catch (Exception e) {
        	return false;
		}
    }

    public Users findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE users_id = ?";
        Users user = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Users(
                        rs.getInt("users_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                );
            }
        }
        return user;
    }

    public List<Users> findAll() throws SQLException {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                users.add(new Users(
                        rs.getInt("users_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                ));
            }
        }
        return users;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE users_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result=stmt.executeUpdate();
            return result>0;
        }
        catch (Exception e) {
        	return false;
		}
    }
}
