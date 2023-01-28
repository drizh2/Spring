DELETE FROM message;

INSERT INTO message(id, tag, text, user_id)
VALUES (1, 'tag1', 'text1', 1),
       (2, 'tag2', 'text2', 1),
       (3, 'tag3', 'text3', 2),
       (4, 'tag4', 'text4', 2);

ALTER SEQUENCE message_seq RESTART WITH 10;