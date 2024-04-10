CREATE TABLE cliente (
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100),
 sobrenome VARCHAR(100),
 cpf CHAR(11),
 telefone NUMERIC ,
 clienteId INTEGER,
 pratosFavoritos VARCHAR(255),
 cards INTEGER,
 foto VARCHAR(255),
 FOREIGN KEY (clienteId) REFERENCES users(id)

);