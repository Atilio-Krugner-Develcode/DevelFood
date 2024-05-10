CREATE TABLE cliente (
 id SERIAL PRIMARY KEY,
 first_name VARCHAR(100),
 last_name VARCHAR(100),
 cpf CHAR(11),
 phone VARCHAR(20),
 clienteId INTEGER,
 pedidoId INTEGER,
 pratos_favoritos VARCHAR(255),
 cards INTEGER,
 image VARCHAR(255),
 email VARCHAR(255),
 FOREIGN KEY (clienteId) REFERENCES users(id)

);