-- 1. `product_link_name` için benzersiz indeks oluşturma
-- Bu, birebir eşleşme sorgularını inanılmaz hızlandırır.
CREATE UNIQUE INDEX products_product_link_name_idx ON products (product_link_name);

-- 2. `products` tablosu için bileşik indeks oluşturma
-- Bu indeks, en sık kullanılan filtreleme koşullarını (silinmemiş, yayınlanmış ürünler vb.)
-- ve fiyat aralığı sorgularını aynı anda optimize eder.
CREATE INDEX products_filter_price_idx ON products (is_deleted, published, product_type, disable_out_of_stock, compare_price);

-- 3. `product_categories` ara tablosu için indeks oluşturma
-- Kategoriye göre yapılan filtreleme sorgularında JOIN işlemini hızlandırır.
CREATE INDEX product_categories_category_id_idx ON product_categories (category_id);
-- 4. `orders` tablosu için indeksler
CREATE UNIQUE INDEX orders_order_code_idx ON orders (order_code);
CREATE INDEX orders_user_id_idx ON orders (user_id);
CREATE INDEX orders_order_status_id_idx ON orders (order_status_id);
CREATE INDEX orders_customer_coupon_id_idx ON orders (customer_coupon_id);
CREATE INDEX orders_total_price_idx ON orders (total_price);
CREATE INDEX orders_created_at_idx ON orders (created_at);

-- 5. `order_statuses` tablosu için indeks
CREATE INDEX order_statuses_status_name_idx ON order_statuses (status_name);

-- 6. `payment` tablosu için indeksler
CREATE INDEX payment_order_id_idx ON payment (order_id);
CREATE INDEX payment_payment_status_idx ON payment (payment_status);
CREATE UNIQUE INDEX payment_conversation_id_idx ON payment (conversation_id);
CREATE UNIQUE INDEX payment_payment_id_idx ON payment (payment_id);
CREATE UNIQUE INDEX IF NOT EXISTS customers_username_idx ON customer (username);
CREATE UNIQUE INDEX IF NOT EXISTS admins_username_idx ON admins (username);