INSERT INTO user(id, email, password)
VALUES (1, 'admin@byom.de', '{noop}asdf'), (2, 'marcin@byom.de', '{noop}asdf'), (3, 'kasia@byom.de', '{noop}asdf');

INSERT INTO user_role(user_id, role)
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER'), (3, 'ROLE_USER');

-- password reset key dodaje sie przy zmianie hasała to to jest nullem
