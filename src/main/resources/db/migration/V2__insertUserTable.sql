INSERT INTO users (id, first_name, last_name, email, created_by, updated_by) VALUES(1, 'Qwenton', 'Caldwell', 'qwentonjc7@gmail.com', 'QWENTONGC', NOW());
ALTER SEQUENCE users_id_seq RESTART WITH 2;