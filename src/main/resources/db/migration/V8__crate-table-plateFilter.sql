CREATE TABLE plate_filter (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    tiposComidaId INTEGER,
    tiposId INTEGER,
    restaurantId INTEGER,
    FOREIGN KEY (tiposComidaId) REFERENCES pratos(id),
    FOREIGN KEY (restaurantId) REFERENCES restaurant(id),
    FOREIGN KEY (tiposId) REFERENCES tiposComida(id)

);

INSERT INTO plate_filter (name, tiposId) VALUES
    ('Prato Italiano 1', 1),
    ('Prato Japonês 1', 2),
    ('Prato Mexicano 1', 3);