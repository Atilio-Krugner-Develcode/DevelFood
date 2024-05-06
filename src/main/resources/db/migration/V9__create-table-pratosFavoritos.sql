CREATE TABLE favorito (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER,
    prato_id INTEGER,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (prato_id) REFERENCES pratos(id)
);
