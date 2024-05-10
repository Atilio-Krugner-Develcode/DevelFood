
CREATE TABLE endereco(
 id SERIAL PRIMARY KEY,
  street VARCHAR(255) NOT NULL,
 number VARCHAR(255) NOT NULL,
 city VARCHAR(255) NOT NULL,
 neigbourhood VARCHAR(255) NOT NULL,
 cep VARCHAR(255),
 state VARCHAR(255),
 enderecoId INTEGER,
 restaurant_id INTEGER,
 cliente_id INTEGER,
 FOREIGN KEY (enderecoId) REFERENCES cliente(id),
 FOREIGN KEY (enderecoId) REFERENCES restaurant(id)
);