CREATE TABLE restaurant(
 id SERIAL PRIMARY KEY,
 name VARCHAR(100) NOT NULL,
 cnpj CHAR(11),
 phone VARCHAR(20),
 restauranteId INTEGER,
 image VARCHAR(255),
 tipos INTEGER,
 pedidosId INTEGER,
 localizacao INTEGER,
 FOREIGN KEY (restauranteId) REFERENCES users(id)

 );
