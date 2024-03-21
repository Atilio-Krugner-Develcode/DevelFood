CREATE TABLE avaliacao(
 id SERIAL PRIMARY KEY NOT NULL,
 descricao VARCHAR(255),
 nota FLOAT,
 tempo DATE,
 avaliacaoId INTEGER,
 FOREIGN KEY (avaliacaoId) REFERENCES cliente(id),
 FOREIGN KEY (avaliacaoId) REFERENCES restaurante(id)
);