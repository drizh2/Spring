DELETE FROM message;
DELETE FROM user_role;
DELETE FROM usr;

INSERT INTO usr(id, username, password, active) VALUES
                                                    (1, 'drizh2', '$2a$08$3vobz9YGY0XYrh3QROk./OlsC/7laS61BaNvITjwNQd6.8UDexpQa', true),
                                                    (2, 'anonymous', '$2a$08$3vobz9YGY0XYrh3QROk./OlsC/7laS61BaNvITjwNQd6.8UDexpQa', true);

INSERT INTO user_role(user_id, roles) VALUES
                                          (1, 'ADMIN'), (1, 'USER'),
                                          (2, 'USER');