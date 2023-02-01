CREATE TABLE message_likes(
    user_id BIGINT NOT NULL REFERENCES usr,
    message_id BIGINT NOT NULL REFERENCES message,
    primary key (user_id, message_id)
);