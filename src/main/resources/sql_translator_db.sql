DROP DATABASE IF EXISTS `TranslatorDB`;

CREATE DATABASE `TranslatorDB`;
USE `TranslatorDB`;

DROP TABLE IF EXISTS `RuWords`;
#Create table with RU words
CREATE TABLE RuWord
(
    `id`       INT(10) NOT NULL AUTO_INCREMENT,
    `word`     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `EnWords`;
#Create table with EN words
CREATE TABLE EnWord
(
    `id`       INT(10) NOT NULL AUTO_INCREMENT,
    `word`     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `EnRuWords`;
#Associate Ru and En words in one database;
CREATE TABLE `EnRuWords`
(
    `id`    INT(10) NOT NULL AUTO_INCREMENT,
    `Ru_id` INT(10) NOT NULL,
    `En_id` INT(10) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `EnId` (`En_id`),

    CONSTRAINT `Ru_id_fk` FOREIGN KEY (`Ru_id`) REFERENCES `RuWord` (`id`),
    CONSTRAINT `En_id_fk` FOREIGN KEY (`En_id`) REFERENCES `EnWord` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    `id`               INT(10) NOT NULL AUTO_INCREMENT,
    `role`             VARCHAR(255) DEFAULT NULL,
    `user_name`        VARCHAR(255) DEFAULT NULL,
    `password`         VARCHAR(255) DEFAULT NULL,
    `word_association` INT(10) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `Ru_En_id_fk` FOREIGN KEY (`word_association`) REFERENCES `EnRuWords` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `user_word_rel`;
CREATE TABLE `user_word_rel`
(
    `user_id`             INT DEFAULT NULL,
    `word_association_id` INT DEFAULT NULL,
    CONSTRAINT `user_id_k` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`),
    CONSTRAINT `words_id_k` FOREIGN KEY (`word_association_id`) REFERENCES `EnRuWords` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE user_word_rel
    ADD INDEX `idx2` (user_id),
    ADD INDEX `idx3` (word_association_id);






