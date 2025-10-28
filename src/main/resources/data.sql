-- data.sql
-- Sample data for testing
INSERT INTO customers (id, name, email) VALUES
('cust-001', 'John Doe', 'john.doe@example.com'),
('cust-002', 'Jane Smith', 'jane.smith@example.com');

INSERT INTO products (id, name, description, price, currency, stock_quantity) VALUES
('prod-001', 'Laptop', 'High-performance laptop', 999.99, 'USD', 10),
('prod-002', 'Mouse', 'Wireless mouse', 29.99, 'USD', 50),
('prod-003', 'Keyboard', 'Mechanical keyboard', 149.99, 'USD', 25);
