CREATE TABLE IF NOT EXISTS roles (
                       rol_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       rol_name VARCHAR(50) NOT NULL UNIQUE,
                       rol_enabled BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
                       usr_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       usr_name VARCHAR(100),
                       usr_username VARCHAR(50) NOT NULL UNIQUE,
                       usr_email VARCHAR(100) UNIQUE,
                       usr_password VARCHAR(200) NOT NULL,
                       usr_address VARCHAR(100),
                       usr_zip_code VARCHAR(6),
                       usr_phone VARCHAR(10),
                       usr_image_url VARCHAR(255),
                       usr_enabled BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_roles (
                            usr_id BIGINT NOT NULL,
                            rol_id BIGINT NOT NULL,
                            FOREIGN KEY (usr_id) REFERENCES users(usr_id)
                                ON DELETE CASCADE,
                            FOREIGN KEY (rol_id) REFERENCES roles(rol_id)
                                ON DELETE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS veterinarians (
                               usr_id BIGINT PRIMARY KEY,
                               vet_specialty VARCHAR(100),
                               vet_veterinary VARCHAR(50),
                               vet_degree_url VARCHAR(255),
                               vet_enabled BOOLEAN DEFAULT TRUE,
                               FOREIGN KEY (usr_id) REFERENCES users(usr_id)
                                   ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contacts (
                          con_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          con_own_id BIGINT NOT NULL,
                          con_vet_id BIGINT NOT NULL,
                          con_status ENUM('PENDING', 'ACCEPTED') NOT NULL,
                          con_created_at DATETIME,
                          FOREIGN KEY (con_own_id) REFERENCES users(usr_id),
                          FOREIGN KEY (con_vet_id) REFERENCES veterinarians(usr_id),
                          UNIQUE KEY unique_contact (con_own_id, con_vet_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pets (
                      pet_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      pet_own_id BIGINT NOT NULL,
                      pet_name VARCHAR(100) NOT NULL,
                      pet_specie VARCHAR(50) NOT NULL,
                      pet_age_months INT,
                      pet_birth_date DATETIME,
                      pet_weight DECIMAL(5,2),
                      pet_image_url VARCHAR(255),
                      pet_enabled BOOLEAN DEFAULT TRUE,
                      FOREIGN KEY (pet_own_id) REFERENCES users(usr_id)
                          ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS appointments (
                              app_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              own_id BIGINT NOT NULL,
                              pet_id BIGINT NOT NULL,
                              vet_id BIGINT NOT NULL,
                              app_datetime DATETIME NOT NULL,
                              app_duration INT NOT NULL,
                              app_reason VARCHAR(250) NOT NULL,
                              app_description TEXT,
                              app_status ENUM('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL,
                              FOREIGN KEY (own_id) REFERENCES users(usr_id)
                                  ON DELETE RESTRICT,
                              FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
                                  ON DELETE RESTRICT,
                              FOREIGN KEY (vet_id) REFERENCES veterinarians(usr_id)
                                  ON DELETE RESTRICT
) ENGINE=InnoDB;

-- En memoria de la tabla Owner (Julio 2024 - Noviembre 2024). Nunca te olvidaremos :'(