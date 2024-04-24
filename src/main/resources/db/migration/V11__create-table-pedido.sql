CREATE TABLE pedido(
 id SERIAL PRIMARY KEY NOT NULL,
 tempoInicial DATE,
 tempoAtualizado DATE,
 total FLOAT,
 quantidade INTEGER,
 tipoPagamento VARCHAR(100),
 estadoServico BOOLEAN,
 pedidore INTEGER,
 pedidoscl INTEGER,
 estatus VARCHAR(255),
 pratos INTEGER,
 FOREIGN KEY (pedidoscl) REFERENCES cliente(id),
 FOREIGN KEY (pedidore) REFERENCES restaurant(id)

);