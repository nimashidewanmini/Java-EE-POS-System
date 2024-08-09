DROP DATABASE IF EXISTS pos_assignment;

CREATE DATABASE pos_assignment;

USE pos_assignment;

CREATE TABLE customer (
                          id VARCHAR(15) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          salary DECIMAL(10,2) NOT NULL
);

CREATE TABLE item (
                      id VARCHAR(15) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      type VARCHAR(50) NOT NULL,
                      price DECIMAL(10,2) NOT NULL,
                      qty INT NOT NULL
);

CREATE TABLE orders (
                        orderId VARCHAR(15) PRIMARY KEY,
                        customerId VARCHAR(15) NOT NULL,
                        date DATE NOT NULL,
                        CONSTRAINT FOREIGN KEY (customerId) REFERENCES customer(id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
);

CREATE TABLE orderDetails (
                              orderId VARCHAR(15),
                              itemId VARCHAR(15),
                              qty INT NOT NULL,
                              total DECIMAL(10,2) NOT NULL,
                              PRIMARY KEY (orderId, itemId),
                              CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders(orderId)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE,
                              CONSTRAINT FOREIGN KEY (itemId) REFERENCES item(id)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE
);
