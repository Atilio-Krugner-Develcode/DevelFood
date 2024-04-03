CREATE TABLE pratos (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(55),
    descricao VARCHAR(255),
    foto VARCHAR(255),
    preco NUMERIC(6, 2),
    categoria VARCHAR(255),
    restauranteId INTEGER,  -- Correção do nome da coluna
    FOREIGN KEY (restauranteId) REFERENCES restaurant(id)
);