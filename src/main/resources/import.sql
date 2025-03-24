-- products
CREATE INDEX idx_product_publish ON products (published);
-- customers
CREATE INDEX idx_customer_email ON customers (username);
-- product_categories
CREATE INDEX idx_product_category ON product_categories (product_id, category_id);
-- product_shipping_info
CREATE INDEX idx_product_shipping_info_product_id ON product_shipping_info (product_id);
