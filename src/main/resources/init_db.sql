CREATE SCHEMA IF NOT EXISTS `chat` DEFAULT CHARACTER SET utf8;
USE `chat`;

CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(55) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

CREATE TABLE `messages` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `content` text NOT NULL,
                            `user_id` bigint NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `messages_fk0` (`user_id`),
                            CONSTRAINT `messages_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

CREATE TABLE `chats` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `type` varchar(55) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

CREATE TABLE `users_chats` (
                               `user_id` bigint NOT NULL,
                               `chat_id` bigint NOT NULL,
                               KEY `users_chats_fk0` (`user_id`),
                               KEY `users_chats_fk1` (`chat_id`),
                               CONSTRAINT `users_chats_fk0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `users_chats_fk1` FOREIGN KEY (`chat_id`) REFERENCES `chats` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

INSERT INTO chats (type) VALUES('main');
