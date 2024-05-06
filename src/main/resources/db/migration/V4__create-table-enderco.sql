
CREATE TABLE endereco(
 id SERIAL PRIMARY KEY,
 rua VARCHAR(255) NOT NULL,
 numero NUMERIC NOT NULL,
 cidade VARCHAR(255) NOT NULL,
 bairro VARCHAR(255) NOT NULL,
 cep VARCHAR(255),
 state VARCHAR(255),
 enderecoId INTEGER,
 restaurant_id INTEGER,
 cliente_id INTEGER,
 FOREIGN KEY (enderecoId) REFERENCES cliente(id),
 FOREIGN KEY (enderecoId) REFERENCES restaurant(id)
);