CREATE TABLE cliente (
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100),
 sobrenome VARCHAR(100),
 cpf CHAR(11),
 telefone VARCHAR(20),
 clienteId INTEGER,
 pedidoId INTEGER,
 pratos_favoritos VARCHAR(255),
 cards INTEGER,
 foto VARCHAR(255),
 email VARCHAR(255),
 FOREIGN KEY (clienteId) REFERENCES users(id)

);