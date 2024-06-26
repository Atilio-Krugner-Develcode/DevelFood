CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    recovery_token_timestamp VARCHAR(255),
    recovery_token VARCHAR(255),
    usuario_id VARCHAR(255),
    user_email VARCHAR(255),
    role TEXT NOT NULL,
    verification_code VARCHAR(255)
);