CREATE TABLE restaurante(
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 cpf CHAR(11),
 telefone NUMERIC,
 restauranteId INTEGER,
 foto VARCHAR(255),
 pratos VARCHAR(255),
 FOREIGN KEY (restauranteId) REFERENCES users(id)

 );
