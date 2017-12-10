-- Insert role(s)
insert into role (name) values ('ROLE_USER');
insert into role (name) values ('ROLE_ADMIN');

-- Insert three users, one admin, and two regular users
-- password for admin is 'admin', password for both user and user2 is 'password'
-- used this online tool for getting the hash https://www.dailycred.com/article/bcrypt-calculator
insert into user (username, enabled, password) values ('admin', true, '$2a$10$.sMJyDcj.7C.lKZaNU8Z9e4XVLw.DsWStl3ztNrp4s607PxUlP4nq');
insert into user (username, enabled, password) values ('user', true, '$2a$10$Z2J2u1JsKTIHkin2wJs5y.gfcrL2FxeDKlWNoniUaHAfGhu3w900W');
insert into user (username, enabled, password) values ('user2', true, '$2a$10$Z2J2u1JsKTIHkin2wJs5y.gfcrL2FxeDKlWNoniUaHAfGhu3w900W');

-- Assign roles to users, admin gets 1 and 2 (USER and ADMIN), user and user2 get 2 only (USER)
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (1, 2);
insert into user_role (user_id, role_id) values (2, 1);
insert into user_role (user_id, role_id) values (3, 1);

-- Insert flash cards
insert into flashcard (term, definition) values ('JDK', 'Java Development Kit');
insert into flashcard (term, definition) values ('YAGNI', 'You Ain''t Gonna Need It');
insert into flashcard (term, definition) values ('Java SE', 'Java Standard Edition');
insert into flashcard (term, definition) values ('Java EE', 'Java Enterprise Edition');
insert into flashcard (term, definition) values ('JRE', 'Java Runtime Environment');
insert into flashcard (term, definition) values ('JCL', 'Java Class Library');
insert into flashcard (term, definition) values ('JVM', 'Java Virtual Machine');
insert into flashcard (term, definition) values ('SDK', 'Software Development Kit');
