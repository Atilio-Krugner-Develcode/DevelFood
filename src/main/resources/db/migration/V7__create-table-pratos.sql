CREATE TABLE pratos(
 id SERIAL PRIMARY KEY NOT NULL,
 nome VARCHAR(55),
 descricao VARCHAR(255),
 foto VARCHAR(255),
 preco NUMERIC(6, 2),
 tipo VARCHAR(255),
 pratosId INTEGER,
  FOREIGN KEY (pratosId) REFERENCES restaurant(id)

);