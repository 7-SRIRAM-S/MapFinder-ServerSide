//$Id$
package com.mapfinder.utils;


public class Queries {
	
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
	public static final String INSERT_ATTEMPT = "INSERT INTO attempts (user_id, map_id, mode_id, score, total_questions, correct_answers, wrong_answers, started_at, ended_at, duration_seconds) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
//	-----------------------------  -------- Leaderboard  ------- -----------------------------------------------------
	
	public static final String VIEW_LEADERBOARD = "SELECT * FROM leaderboard ORDER BY total_score DESC";
	public static final String INSERT_LEADERBOARD = "INSERT INTO leaderboard (user_id, map_id, mode_id, total_score, total_games, average_score, rank_position) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	
//	-------------------------------- -------- Announcement  ------- ------------------------------------------------
	
	public static final String VIEW_ANNOUNCEMENT = "SELECT * FROM announcements WHERE is_active = TRUE";
	public static final String INSERT_ANNOUNCEMENT = "INSERT INTO announcements (title, message, created_by, is_active) VALUES (?, ?, ?, ?)";
	
	
// -------------------------------- ------- friend request  ---------  -----------------------------------------------
	
	public static final String INSERT_FRIEND_REQUEST = "insert into friend_requests (request_id, sender_id, receiver_id, status, created_at) values(? ,? ,?, ?, ?)";
	public static final String GET_FRIENDS = "SELECT u2.* FROM friends f JOIN USERS u1 ON u1.USERS_ID = f.user_id JOIN USERS u2 ON u2.USERS_ID = f.friend_id WHERE u1.USERS_ID = 1;";
	public static final String REMOVE_FRIEND = "delete from friends where friend_id = ? and user_id = ? ";

	
//	------------------------------------ ------ Error states  ------ -------------------------------------------------
	
	public static final String INSERT_ERROR_STATE = "INSERT INTO error_states (user_id, mode_id, state_id, wrong_answer, correct_answer, attempt_number) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String VIEW_ERROR_STATE = "SELECT * FROM error_states WHERE user_id = ?";
}
