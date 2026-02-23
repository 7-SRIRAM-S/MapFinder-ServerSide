package com.mapfinder.utils;

public class QueryUtil {
	
	
//	------------------------------ --------- User queries  ------- --------------------------------------------------

	public static final String CHECK_USERNAME = "SELECT * FROM users WHERE USERNAME=?";
	public static final String INSERT_USER = "INSERT INTO users (USERNAME, PASSWORD) VALUES(?, AES_ENCRYPT(?, ?))";
	public static final String VIEW_USER = "SELECT * FROM users WHERE USERNAME = ? AND PASSWORD = AES_ENCRYPT(?, ?)";
	public static final String GET_USERID_BYNAME="SELECT user_id	FROM users WHERE USERNAME=?";
	public static final String ADD_HINT="UPDATE users SET HINTS = HINTS + 1 WHERE user_id = ? RETURNING HINTS";
	public static final String SELECT_HINT="SELECT HINTS FROM users WHERE user_id = ?";
	public static final String SELECT_USERNAME="SELECT USERNAME FROM users WHERE user_id = ?";
	
	
//	------------------------------ --------- maps queries  ------- --------------------------------------------------
	
	public static final String INSERT_MAP = "INSERT INTO maps (map_name, map_type, is_active) VALUES (?, ?, ?)";
	public static final String VIEWMAP = "SELECT * FROM maps";
	public static final String EDIT_MAP =  "UPDATE maps set is_active=? where map_id=?";
	public static final String DELETE_MAP = "DELETE FROM maps WHERE map_id = ?";
	
	
//	------------------------------ --------    Locations   -------- --------------------------------------------------
	
	public static final String INSERT_LOCATION= "INSERT INTO locations (map_id, location_name, region_type) VALUES (?, ?, ?)";
	public static final String VIEW_LOCATION = "SELECT * From locations";
	public static final String DELETE_LOCATION = "Delete from locations where location_id = ?";
	
	
//	-----------------------------  --------   GameModes   -------  -----------------------------------------------------
	
	public static final String INSERT_MODE = "INSERT INTO game_modes (mode_name, description, time_limit_seconds, is_ranked) VALUES (?, ?, ?, ?)";
	public static final String VIEW_MODES = "select * from game_modes";
	public static final String DELETE_MODE = "Delete from game_modes where mode_id = ?";
	
	
//	------------------------------  -------  Attempts   ---------  --------------------------------------------------
	
	public static final String ATTEMPT_FINDBY_USERID = "SELECT * FROM attempts WHERE user_id = ?";
	public static final String INSERT_ATTEMPT = "INSERT INTO attempts (user_id, mode_id, started_at) VALUES (?, ?, NOW())";
	
	
//	-----------------------------  -------- Leaderboard  ------- -----------------------------------------------------
	
	public static final String VIEW_LEADERBOARD = "SELECT l.leaderboard_id, l.user_id, u.username, l.total_score, l.total_games, l.average_score FROM leaderboard l JOIN users u ON u.user_id = l.user_id ORDER BY total_score desc;";
	public static final String VIEW_TOPFIVE_LEADERBOARD="SELECT l.leaderboard_id, l.user_id, u.username, l.total_score, l.total_games, l.average_score FROM leaderboard l JOIN users u ON u.user_id = l.user_id ORDER BY total_score DESC LIMIT 5;";
	public static final String INSERT_LEADERBOARD = "INSERT INTO leaderboard (user_id, map_id, mode_id, total_score, total_games, average_score, rank_position) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_TOTALSCORE ="select total_score  from leaderboard where user_id = ?";
	
	
//	-------------------------------- -------- Announcement  ------- ------------------------------------------------
	
	public static final String VIEW_ANNOUNCEMENT = "SELECT * FROM announcements WHERE is_active = TRUE and created_by = ? ";
	public static final String INSERT_ANNOUNCEMENT = "INSERT INTO announcements (title, message, created_by, is_active) VALUES (?, ?, ?, ?)";
	
	
// -------------------------------- ------- friend request  ---------  -----------------------------------------------
	
	public static final String INSERT_FRIEND_REQUEST = "insert into friend_requests (sender_id, receiver_id) values(? ,? )";
	public static final String GET_FRIENDS = "SELECT u2.* FROM friends f JOIN users u1 ON u1.user_id = f.user_id JOIN users u2 ON u2.user_id = f.friend_id WHERE u1.user_id = ?;";
	public static final String REMOVE_FRIEND = "delete from friends where friend_id = ? and user_id = ? ";
	public static final String IS_ALREADY_REQUESTED = "select * from friend_requests where sender_id=? and receiver_id=? or sender_id=? and receiver_id=?";
	public static final String IS_FRIEND = "select * from friends where user_id = ? and friend_id = ? or user_id=? and friend_id=?";


	
//	------------------------------------ ------ Error states  ------ -------------------------------------------------
	
	public static final String INSERT_ERROR_STATE = "INSERT INTO error_states (user_id, mode_id,  correct_answer, wrong_answer, attempt_id) VALUES (?, ?, ?, ?, ?)";
	public static final String VIEW_ERROR_STATE = "SELECT * FROM error_states WHERE user_id = ?";
	
	
//	---------------------------------------- ----- certificates -------- -------------------------------------------------------
	
	public static final String INSERT_CERTIFICATE = "insert into certificate (name,rating,issued_by) values(?,?,?)";
	public static final String INSERT_INTO_USER_CERTIFICATE = "insert into user_certificates(user_id ,certificate_id) values(?,?)";
	public static final String GET_CERTIFICATE_USERS = "select count(*) as totalCertificates from user_certificates where user_id= ? ;";

}
