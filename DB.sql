DROP DATABASE IF EXISTS `AM_JDBC_2024_07`;
CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;

SHOW TABLES;

CREATE TABLE article
(
    id         int(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate    DATETIME         NOT NULL,
    updateDate DATETIME         not null,
    title      char(100)        not null,
    `body`     text             not null
);

SELECT *
FROM article;


SELECT substring(rand() * 1000 FROM 1 FOR 2);

INSERT INTO article
    SET regDate = now(),
        updateDate = now(),
        title = CONCAT('제목1', SUBSTRING(RAND() * 1000 From 1 For 2)),
        `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 From 1 For 2));

SELECT *
FROM article;