CREATE TABLE roles(
idRol INT PRIMARY KEY AUTO_INCREMENT,
nombreRol VARCHAR(30) NOT NULL
);

CREATE TABLE Usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    idRol INT NOT NULL,
    activo BOOLEAN DEFAULT true,
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (idRol) REFERENCES roles(idRol),

    PRIMARY KEY(id)
);

CREATE TABLE Curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    activo BOOLEAN DEFAULT true,
    PRIMARY KEY(id)
);

CREATE TABLE Topico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(1000) NOT NULL,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(100) NOT NULL,
    id_autor BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_topico_autor FOREIGN KEY (id_autor) REFERENCES Usuario(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (id_curso) REFERENCES Curso(id)
);

CREATE TABLE Respuesta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(1000) NOT NULL,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_autor BIGINT NOT NULL,
    id_topico BIGINT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (id_autor) REFERENCES Usuario(id),
    CONSTRAINT fk_respuesta_topico FOREIGN KEY (id_topico) REFERENCES Topico(id)
);
