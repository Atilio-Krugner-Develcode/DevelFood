CREATE TABLE pedido (
    id SERIAL PRIMARY KEY NOT NULL,
    total NUMERIC,
    quantidade INTEGER,
    estadoServico BOOLEAN,
    pedidore INTEGER,
    pedidoscl INTEGER,
    estatus VARCHAR(255),
    pratos INTEGER,
    data TIMESTAMP,
    forma_pagamento VARCHAR(255),
    FOREIGN KEY (pedidoscl) REFERENCES cliente(id),
    FOREIGN KEY (pedidore) REFERENCES restaurant(id)
);