
CREATE TABLE endereco(
 id SERIAL PRIMARY KEY,
 rua VARCHAR(255) NOT NULL,
 numero NUMERIC NOT NULL,
 cidade VARCHAR(255) NOT NULL,
 bairro VARCHAR(255) NOT NULL,
 enderecoId INTEGER,
 FOREIGN KEY (enderecoId) REFERENCES cliente(id),
 FOREIGN KEY (enderecoId) REFERENCES restaurante(id)
);