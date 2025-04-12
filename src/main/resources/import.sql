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


INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (1,'adminsupp', 'ADMIN supp', '5551234567','istanbul',1,'ISTANBULUE','note 1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
INSERT INTO suppliers (id,supplier_name,company,phone_number,address_line1,country_id,city,note,created_at,updated_at,created_by,updated_by) VALUES (2,'adminsupp2', 'ADMIN supp2', '5551234567222','istanbul2',1,'ISTANBULUE2','note 2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1);
SELECT setval('supplier_seq', (SELECT MAX(id) FROM suppliers));


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

INSERT INTO products (id,product_name,product_link_name,sale_price,compare_price,buying_price,quantity,short_description,product_description,product_type,published,cover_image_id,disable_out_of_stock,created_at,updated_at,created_by,updated_by,is_deleted) VALUES (1,'ürün 1','urun-1',100.99,90.00,50.00,100,'kısa açıklama','uzun açıklama','SIMPLE',true,null,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,false);
INSERT INTO products (id,product_name,product_link_name,sale_price,compare_price,buying_price,quantity,short_description,product_description,product_type,published,cover_image_id,disable_out_of_stock,created_at,updated_at,created_by,updated_by,is_deleted) VALUES (2,'ürün 2','urun-2',200.99,190.00,150.00,200,'kısa açıklama','uzun açıklama','SIMPLE',true,null,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,false);
SELECT setval('product_seq', (SELECT MAX(id) FROM products));

INSERT INTO product_categories (category_id,product_id) VALUES (1,1);
INSERT INTO product_categories (category_id,product_id) VALUES (1,2);
INSERT INTO product_categories (category_id,product_id) VALUES (2,1);
INSERT INTO product_categories (category_id,product_id) VALUES (2,2);

INSERT INTO product_suppliers (suppliers_id,product_id) VALUES (1,1);
INSERT INTO product_suppliers (suppliers_id,product_id) VALUES (1,2);

INSERT INTO products_tags (tag_id,product_id) VALUES (1,1);
INSERT INTO products_tags (tag_id,product_id) VALUES (2,2);

INSERT INTO cards(id,customer_id) VALUES (1,2);
SELECT setval('card_seq', (SELECT MAX(id) FROM cards));




