-- Inserts test wishes into the DB
-- The user who runs this needs to have the proper permissions.
-- Ideally, only the admin or any superusers should have the
-- permission to drop tables.

DROP TABLE wishes;

CREATE TABLE wishes (
    WID varchar(64) PRIMARY KEY,
    UID varchar(64) NOT NULL,
    BID varchar(64),
    name varchar(80) NOT NULL,
    descr varchar(255),
    price money,
    status int DEFAULT 0,
    dateAdded timestamp --NOT NULL
);

INSERT INTO wishes (WID, UID, BID, name, descr, price, status)
    VALUES ('69', '13', '78', 'a new car', 'A shiny one', 25, 1);

INSERT INTO wishes (WID, UID, BID, name, descr, price, status)
    VALUES ('70', '13', '79', 'an old new car', 'A not so shiny one', 15, 1);


DROP table users;

CREATE TABLE users (
    UID varchar(64) PRIMARY KEY,
    name varchar(255) NOT NULL
);

INSERT INTO users (UID, name)
    VALUES ('13', 'Alex The Great');

INSERT INTO users (UID, name)
    VALUES ('14', 'DJ Polish P');

INSERT INTO users (UID, name)
    VALUES ('78', 'ng38');

INSERT INTO users (UID, name)
    VALUES ('79', 'Little Bitch');

CREATE ROLE wl_app;
GRANT INSERT ON users to wl_app;
GRANT INSERT ON wishes to wl_app;
GRANT UPDATE ON users to wl_app;
GRANT UPDATE ON wishes to wl_app;
GRANT DELETE ON wishes to wl_app;
GRANT SELECT ON wishes to wl_app;
GRANT SELECT ON users to wl_app;
