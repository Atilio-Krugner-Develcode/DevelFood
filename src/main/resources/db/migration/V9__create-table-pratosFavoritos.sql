CREATE TABLE pratosFavoritos(
 id SERIAL PRIMARY KEY NOT NULL,
 pratosFavoritosId INTEGER,
 FOREIGN KEY (pratosFavoritosId) REFERENCES cliente(id),
 FOREIGN KEY (pratosFavoritosId) REFERENCES pratos(id)

);