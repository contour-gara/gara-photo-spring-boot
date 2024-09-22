CREATE TABLE token (
    id integer PRIMARY KEY,
    access_token varchar(100),
    refresh_token varchar(100),
    date_time DATETIME(3)
);
