ALTER TABLE roles AUTO_INCREMENT = 1;

INSERT INTO `roles`(`rol_id`, `rol_name`) VALUES (1,'ADMIN'),(2,'OWNER'), (3,'VET')
ON DUPLICATE KEY UPDATE rol_name = VALUES(rol_name);


