CREATE TABLE IF NOT EXISTS jerseys (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jersey_id VARCHAR(36),
    size VARCHAR(10),
    color VARCHAR(30),
    styles VARCHAR(30),
    quantity INT,
    price DECIMAL(10, 2)
    );
