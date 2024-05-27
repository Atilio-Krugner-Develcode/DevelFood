CREATE TABLE promocao (
    id SERIAL PRIMARY KEY NOT NULL,
    foto VARCHAR(255),
    data_inicial DATE,
    data_final DATE,
    restaurante_id INTEGER,
    ativa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (restaurante_id) REFERENCES restaurant(id)
);