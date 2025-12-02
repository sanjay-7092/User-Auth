CREATE TABLE `user`(
       `id`   BIGINT AUTO_INCREMENT,
       `firstname` VARCHAR(50) NOT NULL,
       `lastname` VARCHAR(50) NOT NULL,
       `password` VARCHAR(50),
       `email` VARCHAR(50) NOT NULL UNIQUE,
       `contact_no` VARCHAR(50),
       `role` VARCHAR(50),
       `dob` DATE,
       PRIMARY KEY(id)
)