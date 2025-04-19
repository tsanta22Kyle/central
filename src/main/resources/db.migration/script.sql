--CREATE DATABASE central;

CREATE TABLE point_of_sale (
                               id VARCHAR PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               api_key VARCHAR(255) ,
                               api_url VARCHAR(255) NOT NULL
);

CREATE TABLE synchronized_sale (
                                   id SERIAL PRIMARY KEY,
                                   point_of_sale_id VARCHAR(36) NOT NULL,
                                   dish_identifier INTEGER NOT NULL,
                                   dish_name VARCHAR(255) NOT NULL,
                                   quantity_sold INTEGER NOT NULL,
                                   synchronization_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE synchronized_dish_order (
                                         id SERIAL PRIMARY KEY,
                                         point_of_sale_id VARCHAR(36) NOT NULL,
                                         dish_id INTEGER NOT NULL,
                                         dish_name VARCHAR(255) NOT NULL,
                                         quantity INTEGER NOT NULL,
                                         synchronization_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE synchronized_status_history (
                                             id SERIAL PRIMARY KEY,
                                             dish_order_id VARCHAR NOT NULL,
                                             status VARCHAR(50) NOT NULL,
                                             timestamp TIMESTAMP NOT NULL
);
