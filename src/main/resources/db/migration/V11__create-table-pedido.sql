CREATE TABLE pedido(
 id SERIAL PRIMARY KEY NOT NULL,
 tempoInicial DATE,
 tempoAtualizado DATE,
 total FLOAT,
 tipoPagamento VARCHAR(100),
 estadoServico BOOLEAN,
 pedidoId INTEGER,
 FOREIGN KEY (pedidoId) REFERENCES cliente(id),
 FOREIGN KEY (pedidoId) REFERENCES restaurant(id)

);