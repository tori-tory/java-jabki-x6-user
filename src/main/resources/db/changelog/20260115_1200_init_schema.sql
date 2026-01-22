CREATE SCHEMA x6_user;

CREATE TABLE IF NOT EXISTS x6_user."user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
	created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (email)
);