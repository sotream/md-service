CREATE TABLE market_data (
                             id SERIAL PRIMARY KEY,
                             symbol VARCHAR(10) NOT NULL,
                             price DECIMAL(15, 2) NOT NULL,
                             timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
