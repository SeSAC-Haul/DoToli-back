INSERT INTO member(id, email, nickname, password)
VALUES (1, 'user1@example.com', 'chan', 'qwer1234');
INSERT INTO member(id, email, nickname, password)
VALUES (2, 'user2@example.com', 'jjong', 'qwer1234');
INSERT INTO team(id, team_name)
VALUES (1, 'Haul');
INSERT INTO team_member(id, member_id, team_id)
VALUES (1, 1, 1);
INSERT INTO team_member(id, member_id, team_id)
VALUES (2, 2, 1);
INSERT INTO task(id, done, created_at, member_id, team_id, content, flag)
VALUES (1, 0, '2024-10-17 10:30:00.000000', 1, 1, 'Task1', 0);
INSERT INTO task(id, done, created_at, member_id, team_id, content, flag)
VALUES (2, 1, '2024-10-16 10:30:00.000000', 2, 1, 'Task2', 0);
INSERT INTO task(id, done, created_at, member_id, team_id, content, flag)
VALUES (3, 0, '2024-10-15 10:30:00.000000', 1, 1, 'Task3', 0);
