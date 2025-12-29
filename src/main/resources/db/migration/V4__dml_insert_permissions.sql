ALTER TABLE `permission` ADD CONSTRAINT uk_constraint_permission_name UNIQUE(`name`);

INSERT INTO `permission` (`name`,`description`,`is_avail`) values
 ("CREATE_USER","Create a user",true),
 ("UPDATE_USER","Update a user",true),
 ("DELETE_USER","Delete a user",true),
 ("GET_USER","Get all user",true),
 ("GET_ROLE","Get all roles",true),
 ("UPDATE_ROLE","Update a role",true),
 ("DELETE_ROLE","Delete a role",true),
 ("CREATE_ROLE","Create a role",true);