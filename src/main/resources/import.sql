INSERT INTO countries (id,iso,upper_name,name,iso3,num_code,phone_code)VALUES (1,'TR','TURKEY','Turkey','TUR',792,90);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (2, 'GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (3, 'US', 'UNITED STATES', 'United States', 'USA', 840, 1);
SELECT setval('countries_seq', (SELECT MAX(id) FROM countries));


INSERT INTO roles (id,role_name) VALUES (1,'ADMIN');
INSERT INTO roles (id,role_name) VALUES (2,'CUSTOMER');
INSERT INTO roles (id,role_name) VALUES (3,'GUEST');
SELECT setval('role_seq', (SELECT MAX(id) FROM roles));

INSERT INTO admins (id,first_name, last_name, phone_number, username, password, enabled, account_non_locked, created_at, updated_at) VALUES (1,'admin', 'admin', '5551234567', 'admin@gmail.com', '{bcrypt}$2a$10$UQBrvOiT/MP9Ywa/mM1J6.uzK4q1IayeuI6Gdthiij2nThiGrqJJi', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO customer (id,first_name, last_name, phone_number, username, password, enabled, account_non_locked, created_at, updated_at) VALUES (2,'customer', 'customer', '5551234568', 'customer@gmail.com', '{bcrypt}$2a$10$/THi9PwabnkH/wEOq6PttuW6Jxg7PUO2CDiB.lXevXwCng8NOAINi', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
SELECT setval('user_seq', (SELECT MAX(id) FROM admins));

INSERT INTO user_roles (role_id,user_id) VALUES (1,1);

INSERT INTO cards(id,customer_id) VALUES (1,2);
SELECT setval('card_seq', (SELECT MAX(id) FROM cards));


INSERT INTO merchant(id,name,address_id,cover_image_id,phone_no,email,min_order_amount,shipping_fee) VALUES (1,'Lity Digital',1,null,'+905075431646','fatihgs133@gmail.com',1000,75);
SELECT setval('merchant_seq',(SELECT MAX(id) FROM merchant));




