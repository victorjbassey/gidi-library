INSERT INTO roles (id, description, title)
VALUES (1, 'Authorized to carry out all CRUD operations on books, and lend books to users', 'ROLE_LIBRARIAN');
INSERT INTO roles (id, description, title)
VALUES (2, 'A regular user that can only borrow books', 'ROLE_USER');
INSERT INTO users (id, username, password, first_name, last_name, status, date_joined)
VALUES (1, 'librarian', '$2a$10$1YM9MAjlNuzBVKHTS0hqv.wnkskb8YU8kiaPK7En86W4t8wjW1OJG', 'Rachel', 'Green', 'active', '2020-03-19 19:11:02.916071');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);