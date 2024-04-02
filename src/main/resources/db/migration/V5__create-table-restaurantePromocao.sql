CREATE TABLE restaurante_promocao(
 id SERIAL PRIMARY KEY NOT NULL,
 nome VARCHAR(55),
 foto VARCHAR(255),
 data_inicial DATE,
 data_final DATE,
 porcentagem NUMERIC(3, 2),
 promocaoId INTEGER,
 FOREIGN KEY (promocaoId) REFERENCES restaurante(id)

);