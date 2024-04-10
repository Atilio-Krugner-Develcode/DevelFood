CREATE TABLE email (
    id SERIAL PRIMARY KEY NOT NULL,
    owner_ref VARCHAR(255),
    email_from VARCHAR(255),
    email_to VARCHAR(255),
    title VARCHAR(255),
    texto TEXT,
    tempo TIMESTAMP,
    status VARCHAR(255)
);
CREATE SEQUENCE IF NOT EXISTS email_id_seq START 1;


