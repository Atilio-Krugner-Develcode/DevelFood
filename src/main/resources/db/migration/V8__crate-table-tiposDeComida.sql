CREATE TABLE tiposDeComida(
 id SERIAL PRIMARY KEY NOT NULL,
 nome VARCHAR(255),
 tiposComidaId INTEGER,
 FOREIGN KEY (tiposComidaId) REFERENCES pratos(id),
 FOREIGN KEY (tiposComidaId) REFERENCES restaurante(id)

);