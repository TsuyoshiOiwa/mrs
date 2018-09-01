INSERT INTO meeting_room (room_name) VALUES ('新橋');
INSERT INTO meeting_room (room_name) VALUES ('愛');
INSERT INTO meeting_room (room_name) VALUES ('石');
INSERT INTO meeting_room (room_name) VALUES ('海');
INSERT INTO meeting_room (room_name) VALUES ('海老');
INSERT INTO meeting_room (room_name) VALUES ('お尻');

INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE+1, 1);
INSERT INTO reservable_room (reserved_date, room_id) VALUES (CURRENT_DATE-1, 1);