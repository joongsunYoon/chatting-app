-- USERS (user_id: 1~6)
INSERT INTO users (user_id, username, user_handle, password_hash, nickname, gender, birthdate, bio, created_at, updated_at)
VALUES
    (1, 'alice', 'alice1', 'pw1', 'Alice', 'F', '2000-01-01', 'Hi', NOW(), NOW()),
    (2, 'bob', 'bob2', 'pw2', 'Bob', 'M', '2000-02-01', 'Hello', NOW(), NOW()),
    (3, 'charlie', 'char3', 'pw3', 'Charlie', 'M', '2000-03-01', 'Hey', NOW(), NOW()),
    (4, 'daisy', 'daisy4', 'pw4', 'Daisy', 'F', '2000-04-01', 'Yo', NOW(), NOW()),
    (5, 'eric', 'eric5', 'pw5', 'Eric', 'M', '2000-05-01', 'Howdy', NOW(), NOW()),
    (6, 'fiona', 'fiona6', 'pw6', 'Fiona', 'F', '2000-06-01', 'Wassup', NOW(), NOW());

-- ✅ CASE 1: user 1 ↔ user 2 (한 쌍 → 채팅방 생성)
INSERT INTO affinity (to_user_id, from_user_id, affinity_score, created_at, updated_at)
VALUES
    (2, 1, 5, NOW(), NOW()),  -- user 1 → user 2
    (1, 2, 6, NOW(), NOW());  -- user 2 → user 1

-- ✅ CASE 2: user 3 ↔ user 4 vs user 3 ↔ user 5 (3↔4만 선택)
INSERT INTO affinity (to_user_id, from_user_id, affinity_score, created_at, updated_at)
VALUES
    (4, 3, 8, NOW(), NOW()),
    (3, 4, 6, NOW(), NOW()),
    (5, 3, 9, NOW(), NOW()),
    (3, 5, 4, NOW(), NOW());

-- ✅ CASE 3: user 6 ↔ user 4 vs user 6 ↔ user 5 (동점 → 준 하트 비교)
INSERT INTO affinity (to_user_id, from_user_id, affinity_score, created_at, updated_at)
VALUES
    (4, 6, 7, NOW(), NOW()),  -- 6→4
    (6, 4, 3, NOW(), NOW()),  -- 4→6
    (5, 6, 6, NOW(), NOW()),  -- 6→5
    (6, 5, 4, NOW(), NOW());  -- 5→6

-- CASE 4 수정
INSERT INTO affinity (to_user_id, from_user_id, affinity_score, created_at, updated_at)
VALUES
    (5, 1, 6, DATEADD('DAY', -2, NOW()), NOW()),
    (1, 5, 2, NOW(), NOW()),
    (6, 1, 4, DATEADD('DAY', -3, NOW()), NOW()),
    (1, 6, 3, NOW(), NOW());

-- ✅ CASE 5 : user 2 ↔ user 3 vs user 2 ↔ user 4 (완전동점 → 먼저 하트 준 사람 선택)
INSERT INTO affinity (to_user_id, from_user_id, affinity_score, created_at, updated_at)
VALUES
    (3, 2, 5, '2024-01-01 12:00:00', NOW()),
    (2, 3, 5, NOW(), NOW()),
    (4, 2, 5, '2024-01-01 13:00:00', NOW()),
    (2, 4, 5, NOW(), NOW());

-- 1. 채팅방 생성
INSERT INTO chat_room (created_at, updated_at)
VALUES (NOW(), NOW());

-- 2. 방 번호 확인 (자동 증가된 chat_room_id 확인 필요)
-- 예: 방 번호가 1이라고 가정할 경우 아래와 같이 insert
INSERT INTO chat_room_members (room_id, user_id, joined_at)
VALUES
    (1, 1, NOW()),  -- user 1 참가
    (1, 2, NOW());  -- user 2 참가
