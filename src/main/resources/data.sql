-- Insert sample products
INSERT INTO product (name, price, vat_rate) VALUES ('Product A', 10.00, 0.10);
INSERT INTO product (name, price, vat_rate) VALUES ('Product B', 20.00, 0.20);
INSERT INTO product (name, price, vat_rate) VALUES ('Product C', 15.00, 0.15);

-- Insert a sample order
INSERT INTO orders (order_price, order_vat) VALUES (75.00, 7.50);

-- Insert items for the sample order
INSERT INTO order_item (order_id, product_id, quantity, price, vat)
VALUES (1, 1, 2, 20.00, 2.00); -- 2 units of Product A
INSERT INTO order_item (order_id, product_id, quantity, price, vat)
VALUES (1, 2, 1, 20.00, 4.00); -- 1 unit of Product B
INSERT INTO order_item (order_id, product_id, quantity, price, vat)
VALUES (1, 3, 3, 45.00, 1.50); -- 3 units of Product C
