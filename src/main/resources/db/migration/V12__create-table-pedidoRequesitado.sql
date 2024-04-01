CREATE TABLE pedidoRequisitado(
 id SERIAL PRIMARY KEY NOT NULL,
 pratoNome VARCHAR(255),
 quantidade INT,
 preco FLOAT,
 observacao VARCHAR(255),
 pedidoRquisitadoId INTEGER,
 FOREIGN KEY (pedidoRquisitadoId) REFERENCES pedido(id)

);