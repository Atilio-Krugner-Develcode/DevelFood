CREATE TABLE pedido (
    id SERIAL PRIMARY KEY NOT NULL,
    full_price NUMERIC,
     quantity INTEGER,
    estadoServico BOOLEAN,
    pedidore INTEGER,
    pedidoscl INTEGER,
    status VARCHAR(255),
    pratos INTEGER,
    date TIMESTAMP,
    payment_type VARCHAR(255),
    FOREIGN KEY (pedidoscl) REFERENCES cliente(id),
    FOREIGN KEY (pedidore) REFERENCES restaurant(id)
);