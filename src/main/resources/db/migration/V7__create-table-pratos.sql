CREATE TABLE pratos (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(55),
    description VARCHAR(255),
    image VARCHAR(255),
    price NUMERIC(6, 2),
    category VARCHAR(255),
    restauranteId INTEGER,
    FOREIGN KEY (restauranteId) REFERENCES restaurant(id)
);