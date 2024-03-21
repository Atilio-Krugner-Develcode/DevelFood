CREATE TABLE cliente (
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 sobrenome VARCHAR(100) NOT NULL,
 cpf CHAR(11),
 telefone NUMERIC ,
 clienteId INTEGER,
 pratosFavoritos VARCHAR(255),
 cards INTEGER,
 FOREIGN KEY (clienteId) REFERENCES users(id)

);