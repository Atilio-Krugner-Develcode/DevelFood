CREATE TABLE tiposComida (
    id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(255)
);

INSERT INTO tiposComida (nome) VALUES
    ('Italiano'),
    ('Japonês'),
    ('Mexicano');