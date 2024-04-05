CREATE TABLE plate_filter (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(255),
    tiposComidaId INTEGER,
    restaurantId INTEGER,
    FOREIGN KEY (tiposComidaId) REFERENCES pratos(id),
    FOREIGN KEY (restaurantId) REFERENCES restaurant(id)
);