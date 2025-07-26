INSERT INTO roles (idRol, nombreRol)
VALUES (1, 'ADMIN');

INSERT INTO roles (idRol, nombreRol)
VALUES (2, 'USER');

INSERT INTO Usuario (nombre, correo, contrasena, idRol)
VALUES (
    'Admin',
    'admin@outlook.com',
    '$2a$12$1I9WngTHzkWZU7rsCnuc2Oh7QYoa5etk1ndXup.lA46W7279DPOGS',
    1
);
