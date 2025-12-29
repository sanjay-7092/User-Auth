ALTER TABLE `roles` ADD CONSTRAINT uks_role_name UNIQUE(`name`);

ALTER TABLE `user` DROP COLUMN role;

CREATE TABLE `user_role`(
    `user_id` BIGINT,
    `role_id` BIGINT,
     CONSTRAINT fks_user_with_role_id FOREIGN KEY(`user_id`) REFERENCES user(`id`),
     CONSTRAINT fks_role_with_user_id FOREIGN KEY(`role_id`) REFERENCES roles(`id`)
);