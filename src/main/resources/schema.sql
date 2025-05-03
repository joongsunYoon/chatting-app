-- USERS TABLE
CREATE TABLE users (
                       user_id INT PRIMARY KEY,
                       username VARCHAR(255),
                       user_handle VARCHAR(255),
                       password_hash VARCHAR(255),
                       nickname VARCHAR(255),
                       gender VARCHAR(10),
                       birthdate DATE, -- ✅ 이 줄 추가
                       bio VARCHAR(255),
                       created_at DATETIME,
                       updated_at DATETIME
);

-- CHAT ROOMS TABLE
CREATE TABLE chat_room (
                            chat_room_id LONG AUTO_INCREMENT PRIMARY KEY,
                            created_at DATETIME,
                            updated_at DATETIME
);

-- CHAT ROOM MEMBERS TABLE (중간 테이블: N:N → 1:N + N:1 정규화)
CREATE TABLE chat_room_members (
                                   chat_room_members_id LONG AUTO_INCREMENT PRIMARY KEY ,
                                   room_id LONG,
                                   user_id LONG,
                                   joined_at DATETIME,
                                   FOREIGN KEY (room_id) REFERENCES chat_room(chat_room_id),
                                   FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- MESSAGES TABLE
CREATE TABLE message (
                          message_id LONG AUTO_INCREMENT PRIMARY KEY,
                          message VARCHAR(255),
                          sent_at DATETIME,
                          is_read BOOLEAN,
                          room_id LONG,
                          sender_id LONG,
                          FOREIGN KEY (room_id) REFERENCES chat_room(chat_room_id),
                          FOREIGN KEY (sender_id) REFERENCES users(user_id)
);

-- BLOCKS TABLE
CREATE TABLE blocks (
                        blocker_id LONG,
                        blocked_id LONG,
                        created_at DATETIME,
                        updated_at DATETIME,
                        PRIMARY KEY (blocker_id, blocked_id),
                        FOREIGN KEY (blocker_id) REFERENCES users(user_id),
                        FOREIGN KEY (blocked_id) REFERENCES users(user_id)
);

-- AFFINITIES TABLE
CREATE TABLE affinity (
                            affinity_id LONG AUTO_INCREMENT PRIMARY KEY ,
                            to_user_id LONG,
                            from_user_id LONG,
                            affinity_score LONG,
                            last_change_direction ENUM('+', '-') DEFAULT '+',
                            created_at DATETIME,
                            updated_at DATETIME,
                            FOREIGN KEY (to_user_id) REFERENCES users(user_id),
                            FOREIGN KEY (from_user_id) REFERENCES users(user_id)
);

-- REPORTS TABLE
CREATE TABLE report (
                         report_id LONG AUTO_INCREMENT PRIMARY KEY,
                         reason VARCHAR(255),
                         reported_at DATETIME,
                         reporter_id LONG,
                         reported_id LONG,
                         created_at DATETIME,
                         updated_at DATETIME,
                         FOREIGN KEY (reporter_id) REFERENCES users(user_id),
                         FOREIGN KEY (reported_id) REFERENCES users(user_id)
);

-- FRIENDS TABLE
CREATE TABLE friend (
                         user_id LONG,
                         friend_id LONG,
                         created_at DATETIME,
                         updated_at DATETIME,
                         status ENUM('requested', 'accepted', 'blocked') DEFAULT 'accepted',
                         PRIMARY KEY (user_id, friend_id),
                         FOREIGN KEY (user_id) REFERENCES users(user_id),
                         FOREIGN KEY (friend_id) REFERENCES users(user_id)
);

-- PHOTOS TABLE
CREATE TABLE photo (
                        photo_id LONG AUTO_INCREMENT PRIMARY KEY,
                        user_id LONG,
                        image_url VARCHAR(512),
                        created_at DATETIME,
                        updated_at DATETIME,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);
