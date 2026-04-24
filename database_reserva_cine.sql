DROP DATABASE IF EXISTS reserva_cine_db;
CREATE DATABASE reserva_cine_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE reserva_cine_db;
CREATE TABLE perfiles (
    id_perfil BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    username VARCHAR(45) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    fecha_registro DATE
);

CREATE TABLE usuario_perfiles (
    username VARCHAR(45) NOT NULL,
    id_perfil BIGINT NOT NULL,
    PRIMARY KEY (username, id_perfil),
    CONSTRAINT fk_usuario_perfiles_usuario
        FOREIGN KEY (username) REFERENCES usuarios(username),
    CONSTRAINT fk_usuario_perfiles_perfil
        FOREIGN KEY (id_perfil) REFERENCES perfiles(id_perfil)
);

CREATE TABLE tipos (
    id_tipo BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE eventos (
    id_evento BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    fecha_inicio DATE NOT NULL,
    duracion INT,
    direccion VARCHAR(255),
    estado VARCHAR(20) NOT NULL,
    destacado BOOLEAN NOT NULL,
    aforo_maximo INT NOT NULL,
    minimo_asistencia INT,
    precio DECIMAL(10,2) NOT NULL,
    id_tipo BIGINT NOT NULL,
    CONSTRAINT fk_eventos_tipo
        FOREIGN KEY (id_tipo) REFERENCES tipos(id_tipo)
);

CREATE TABLE reservas (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio_venta DOUBLE NOT NULL,
    fecha_reserva DATE,
    id_evento BIGINT NOT NULL,
    username VARCHAR(45) NOT NULL,
    cancelada BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_reservas_evento
        FOREIGN KEY (id_evento) REFERENCES eventos(id_evento),
    CONSTRAINT fk_reservas_usuario
        FOREIGN KEY (username) REFERENCES usuarios(username)
);

INSERT INTO perfiles (nombre) VALUES
('ROLE_ADMON'),
('ROLE_CLIENTE');

INSERT INTO usuarios (username, password, email, nombre, apellidos, direccion, enabled, fecha_registro) VALUES
('admin', '$2a$10$yLDuGT9qp4ug7ekgjdzCkumFOaDzj0ISqh1iawJplaOmMCLI97Aga', 'admin@reservacine.com', 'Admin', 'Sistema', 'Calle Principal 1', true, CURDATE()),
('cliente1', '$2a$10$frhdw.NKKh7UdBMBQOYxtO.AjvJdkyTEzHImrJHsq62f9TaDrWLQe', 'cliente1@email.com', 'Laura', 'García', 'Calle Cine 10', true, CURDATE()),
('cliente2', '$2a$10$frhdw.NKKh7UdBMBQOYxtO.AjvJdkyTEzHImrJHsq62f9TaDrWLQe', 'cliente2@email.com', 'Mario', 'López', 'Avenida Película 20', true, CURDATE());

INSERT INTO usuario_perfiles (username, id_perfil) VALUES
('admin', 1),
('cliente1', 2),
('cliente2', 2);

INSERT INTO tipos (nombre, descripcion) VALUES
('Estreno', 'Películas de estreno reciente'),
('Cine clásico', 'Películas clásicas y ciclos especiales'),
('Infantil', 'Sesiones para público infantil'),
('Terror', 'Películas de terror y suspense'),
('Versión original', 'Películas en versión original subtitulada');

INSERT INTO eventos (nombre, descripcion, fecha_inicio, duracion, direccion, estado, destacado, aforo_maximo, minimo_asistencia, precio, id_tipo) VALUES
('Dune: Parte Dos', 'Sesión especial de ciencia ficción.', '2026-05-10', 166, 'Sala 1 - Cine Central', 'ACTIVO', true, 120, 10, 9.50, 1),
('Casablanca', 'Proyección clásica en versión restaurada.', '2026-05-15', 102, 'Sala 2 - Cine Central', 'ACTIVO', true, 80, 5, 6.00, 2),
('Toy Story', 'Sesión infantil de fin de semana.', '2026-05-18', 81, 'Sala 3 - Cine Central', 'ACTIVO', false, 100, 10, 5.50, 3),
('Nosferatu', 'Ciclo especial de cine de terror.', '2026-05-22', 94, 'Sala 4 - Cine Central', 'ACTIVO', false, 70, 8, 7.00, 4),
('Lost in Translation', 'Película en versión original subtitulada.', '2026-05-25', 102, 'Sala 5 - Cine Central', 'ACTIVO', true, 90, 10, 7.50, 5);

INSERT INTO reservas (cantidad, precio_venta, fecha_reserva, id_evento, username, cancelada) VALUES
(2, 19.00, CURDATE(), 1, 'cliente1', false),
(1, 6.00, CURDATE(), 2, 'cliente1', false),
(3, 16.50, CURDATE(), 3, 'cliente2', false),
(1, 7.00, CURDATE(), 4, 'cliente2', true);
