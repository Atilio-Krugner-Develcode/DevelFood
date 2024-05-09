CREATE TABLE promocao (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(55),
    foto VARCHAR(255),
    data_inicial DATE,
    data_final DATE,
    restaurante_id INTEGER,
    ativa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (restaurante_id) REFERENCES restaurant(id)
);