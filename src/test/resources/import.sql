--会議室登録
INSERT INTO meeting_room (room_name) VALUES ('新橋');
INSERT INTO meeting_room (room_name) VALUES ('愛');
INSERT INTO meeting_room (room_name) VALUES ('石');
INSERT INTO meeting_room (room_name) VALUES ('海');
INSERT INTO meeting_room (room_name) VALUES ('海老');
INSERT INTO meeting_room (room_name) VALUES ('お尻');

--ユーザー登録
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('Hiroshi', 'Hiroshi', '田中', 'Hiroshi', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('Takeshi', '猛', '秋田', '猛', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('Akane', 'あかね', 'Gilvert', 'あかね', 'USER');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('Kirito', 'キリト', 'もんじゃ', 'キリト', 'ADMIN');
INSERT INTO usr (user_id, first_name, last_name, password, role_name) VALUES ('1111', '123??11', '大和', '123??11', 'ADMIN');

--会議室利用可能状況登録
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE+1, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE-1, 1);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE+1, 2);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE-1, 2);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE+1, 3);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE-1, 3);

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 4);

--予約登録

INSERT INTO reservation (start_time, end_time, reserved_date, room_id, user_id) VALUES (parsedatetime('8:00', 'HH:mm'), parsedatetime('13:00', 'HH:mm'), CURRENT_DATE, 2, 'Takeshi');
