CREATE TABLE IF NOT EXISTS orders (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(50),
    customer_id VARCHAR(50),
    jersey_id VARCHAR(50),
    team_id VARCHAR(50),
    order_date  VARCHAR(30),
    total_price_order DOUBLE,
    quantity INT
    );


