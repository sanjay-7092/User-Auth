CREATE TABLE `roles`(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(500)
);

CREATE TABLE `permission`(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(500),
    `is_avail` BOOLEAN DEFAULT true
);

CREATE TABLE `role_permissions`(
    `role_id` BIGINT NOT NULL,
    `permission_id` BIGINT NOT NULL,
    PRIMARY KEY(`role_id`,`permission_id`),
    CONSTRAINT fkc_role_id FOREIGN KEY(`role_id`) REFERENCES roles(`id`),
    CONSTRAINT fkc_permission_id FOREIGN KEY(`permission_id`) REFERENCES permission(`id`)
)