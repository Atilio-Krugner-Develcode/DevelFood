CREATE TABLE restaurant(
 id SERIAL PRIMARY KEY,
 nome VARCHAR(100) NOT NULL,
 cnpj CHAR(11),
 telefone NUMERIC,
 restauranteId INTEGER,
 foto VARCHAR(255),
 tipos INTEGER,
 pedidosId INTEGER,
 localizacao INTEGER,
 FOREIGN KEY (restauranteId) REFERENCES users(id)

 );
