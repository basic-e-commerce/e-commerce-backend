INSERT INTO countries (id,iso,upper_name,name,iso3,num_code,phone_code)VALUES (1,'TR','TURKEY','Turkey','TUR',792,90);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (2, 'GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (3, 'US', 'UNITED STATES', 'United States', 'USA', 840, 1);

INSERT INTO roles (id,role_name) VALUES (1,'ADMIN');
INSERT INTO roles (id,role_name) VALUES (2,'CUSTOMER');

INSERT INTO admins (id,first_name, last_name, phone_number, username, password, enabled, account_non_locked, created_at, updated_at) VALUES (1,'admin', 'admin', '5551234567', 'admin@gmail.com', '{bcrypt}$2a$10$UQBrvOiT/MP9Ywa/mM1J6.uzK4q1IayeuI6Gdthiij2nThiGrqJJi', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO user_roles (role_id,user_id) VALUES (1,1);

INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,address_line2,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (1,'adminsupp', 'ADMIN supp', '5551234567','istanbul','bagcilar',1,'ISTANBULUE','note 1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,address_line2,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (2,'adminsupp2', 'ADMIN supp2', '5551234567222','istanbul2','bagcilar2',1,'ISTANBULUE2','note 2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);

INSERT INTO tags (id,tag_name,created_at,updated_at,created_by,updated_by) VALUES (1,'tag 1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO tags (id,tag_name,created_at,updated_at,created_by,updated_by) VALUES (1,'tag 2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);

