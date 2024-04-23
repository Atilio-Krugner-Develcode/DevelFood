CREATE TABLE restaurant(
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 cpf CHAR(11),
 telefone NUMERIC,
 restauranteId INTEGER,
 foto VARCHAR(255),
 tipos INTEGER,
 FOREIGN KEY (restauranteId) REFERENCES users(id)

 );
