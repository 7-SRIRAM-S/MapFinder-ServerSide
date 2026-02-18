CREATE DATABASE mapfinder IF NOT EXISTS;

USE mapfinder;

CREATE TABLE USERS(USERS_ID INT PRIMARY KEY AUTO_INCREMENT, USERNAME VARCHAR(65), PASSWORD VARBINARY(255), ROLE ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER', CREATED_AT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, HINTS INT DEFAULT 1);

CREATE TABLE maps (

    map_id INT AUTO_INCREMENT PRIMARY KEY,

    map_name VARCHAR(100) NOT NULL,

    map_type VARCHAR(50),

    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE locations (

    location_id INT AUTO_INCREMENT PRIMARY KEY,

    map_id INT NOT NULL,

    location_name VARCHAR(100) NOT NULL,

    region_type VARCHAR(50), 

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (map_id) REFERENCES maps(map_id)

        ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE map_locations (

    map_id INT,

    location_id INT,

    PRIMARY KEY (map_id, location_id),

    FOREIGN KEY (map_id) REFERENCES maps(map_id) ON DELETE CASCADE,

    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE CASCADE

);

CREATE TABLE game_modes (

    mode_id INT AUTO_INCREMENT PRIMARY KEY,

    mode_name VARCHAR(50) NOT NULL,

    description TEXT,

    time_limit_seconds INT,

    is_ranked BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE attempts (

    attempt_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,

    map_id INT NOT NULL,

    mode_id INT NOT NULL,

    

    score INT DEFAULT 0,

    total_questions INT,

    correct_answers INT DEFAULT 0,

    wrong_answers INT DEFAULT 0,

    

    started_at DATETIME,

    ended_at DATETIME,

    duration_seconds INT,

    

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,

    FOREIGN KEY (map_id) REFERENCES maps(map_id),

    FOREIGN KEY (mode_id) REFERENCES game_modes(mode_id)

);

CREATE TABLE leaderboard (

    leaderboard_id INT AUTO_INCREMENT PRIMARY KEY,

    user_id INT NOT NULL,

    map_id INT,

    mode_id INT,

    total_score INT DEFAULT 0,

    total_games INT DEFAULT 0,

    average_score DECIMAL(5,2),

    rank_position INT,

    

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE

);

CREATE TABLE announcements (

    announcement_id INT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(200),

    message TEXT,

    created_by INT,

    is_active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    

    FOREIGN KEY (created_by) REFERENCES users(user_id)

);

CREATE TABLE friend_requests (

    request_id INT AUTO_INCREMENT PRIMARY KEY,

    sender_id INT NOT NULL,

    receiver_id INT NOT NULL,

    status ENUM('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    

    FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE,

    FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE

);

CREATE TABLE friends (

    user_id INT,

    friend_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    

    PRIMARY KEY (user_id, friend_id),

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,

    FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE CASCADE

);

CREATE TABLE error_states (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    user_id INT NOT NULL,

    mode_id INT NOT NULL,

    location_id INT NOT NULL,

    wrong_answer VARCHAR(255),

    correct_answer VARCHAR(255),

    attempt_number INT DEFAULT 1,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 

        ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,

    FOREIGN KEY (mode_id) REFERENCES game_modes(mode_id) ON DELETE CASCADE,

    FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE CASCADE

);



CREATE TABLE user_certificates (
       id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT NOT NULL,
	certificate_id INT NOT NULL,
       issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (user_id) REFERENCES users(user_id),
       FOREIGN KEY (certificate_id) REFERENCES certificates(certificate_id)
 );
 
 
 
 
 
 CREATE TABLE certificates (id int primary key auto_increment, name varchar(65), rating enum('MASTER','INTERMEDIATE','BEGINNER'), issued_by varchar(100));


