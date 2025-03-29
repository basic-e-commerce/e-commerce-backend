INSERT INTO countries (id,iso,upper_name,name,iso3,num_code,phone_code)VALUES (1,'TR','TURKEY','Turkey','TUR',792,90);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (2, 'GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (3, 'US', 'UNITED STATES', 'United States', 'USA', 840, 1);
SELECT setval('countries_seq', (SELECT MAX(id) FROM countries));


INSERT INTO roles (id,role_name) VALUES (1,'ADMIN');
INSERT INTO roles (id,role_name) VALUES (2,'CUSTOMER');
SELECT setval('role_seq', (SELECT MAX(id) FROM roles));


INSERT INTO admins (id,first_name, last_name, phone_number, username, password, enabled, account_non_locked, created_at, updated_at) VALUES (1,'admin', 'admin', '5551234567', 'admin@gmail.com', '{bcrypt}$2a$10$UQBrvOiT/MP9Ywa/mM1J6.uzK4q1IayeuI6Gdthiij2nThiGrqJJi', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
SELECT setval('user_seq', (SELECT MAX(id) FROM admins));

INSERT INTO user_roles (role_id,user_id) VALUES (1,1);


INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,address_line2,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (1,'adminsupp', 'ADMIN supp', '5551234567','istanbul','bagcilar',1,'ISTANBULUE','note 1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,address_line2,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (2,'adminsupp2', 'ADMIN supp2', '5551234567222','istanbul2','bagcilar2',1,'ISTANBULUE2','note 2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
SELECT setval('merchant_seq', (SELECT MAX(id) FROM suppliers));


INSERT INTO tags (id,tag_name,created_at,updated_at,created_by,updated_by) VALUES (1,'tag 1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO tags (id,tag_name,created_at,updated_at,created_by,updated_by) VALUES (2,'tag 2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
SELECT setval('tag_seq', (SELECT MAX(id) FROM tags));


INSERT INTO attribute (id,attribute_name,create_at,updated_at,created_by,updated_by) VALUES (1,'Renk',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO attribute (id,attribute_name,create_at,updated_at,created_by,updated_by) VALUES (2,'Beden',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
SELECT setval('attribute_seq', (SELECT MAX(id) FROM attribute));


INSERT INTO attribute_value (id,attribute_id,value,create_at,update_at,created_by,updated_by) VALUES (1,2,'X',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO attribute_value (id,attribute_id,value,create_at,update_at,created_by,updated_by) VALUES (2,2,'XL',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
SELECT setval('attribute_value_seq', (SELECT MAX(id) FROM attribute_value));


INSERT INTO categories (id,category_name,category_link_name,category_description,cover_image_id,active,created_at,updated_at,created_by,updated_by,parent_id,is_sub_category) VALUES (1,'Kadın','Kadın','acıklama',null,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,null,true);
INSERT INTO categories (id,category_name,category_link_name,category_description,cover_image_id,active,created_at,updated_at,created_by,updated_by,parent_id,is_sub_category) VALUES (2,'Erkek','Erkek','acıklama',null,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,null,true);
SELECT setval('category_seq', (SELECT MAX(id) FROM categories));
