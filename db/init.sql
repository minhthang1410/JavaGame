CREATE DATABASE IF NOT EXISTS konoha;

USE konoha;

CREATE TABLE users (
    id INT(3) NOT NULL auto_increment,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(250),
    bio VARCHAR(250),
    money INT unsigned,
    credit_card VARCHAR(30),
    avatar VARCHAR(50),
    role VARCHAR(10),
    kyc bool,
    PRIMARY KEY (id)
);

INSERT INTO users (username, password, email, bio, money, credit_card, avatar, role, kyc) VALUES ("admin", "s3cr3tp4ssw0rd", "admin@konoha88.com", "Try hack me", 999999999, "v3rys3cr3t", "pain.jpg", "player", true)