INSERT INTO user(id, username, password)
VALUES
       (1, 'admin', ''),
       (2, 'marcin', ''),
       (3, 'rafal', '');

INSERT INTO user_role(user_id, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER'), (3, 'ROLE_USER');