CREATE TABLE songs (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    artist VARCHAR(100) NOT NULL,
    album VARCHAR(100) NOT NULL,
    duration VARCHAR(5) NOT NULL,
    release_year VARCHAR(4) NOT NULL
);