INSERT INTO orders (order_id, customer_id, jersey_id, team_id, order_date, total_price_order) VALUES
            ('order1', 'c3540a89-cb47-4c96-888e-ff96708db4d8', '2', 'Forward'),
            ('p2', 'Jane', 'Smith', 'Goalkeeper');

INSERT INTO teams (team_id, name, country) VALUES
            ('t1', 'The Eagles', 'USA'),
            ('t2', 'The Dragons', 'UK');

id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(50),
    customer_id VARCHAR(50),
    jersey_id VARCHAR(50),
    team_id VARCHAR(50),
    order_date  VARCHAR(30),
    total_price_order DOUBLE
