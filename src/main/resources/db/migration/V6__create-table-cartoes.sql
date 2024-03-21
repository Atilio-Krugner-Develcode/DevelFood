CREATE TABLE cartoes(
 id SERIAL PRIMARY KEY NOT NULL,
 numero NUMERIC,
 bandeira VARCHAR(255),
 descricao VARCHAR(255),
 calendario DATE,
 cartaoId INTEGER,
 FOREIGN KEY (cartaoId) REFERENCES cliente(id)
);