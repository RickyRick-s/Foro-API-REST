# ForoAPI - API REST para Foro

ForoAPI es una API REST construida con Java y Spring Boot para gestionar usuarios, cursos, tópicos y respuestas en un foro con autenticación JWT, control de roles, paginación y manejo de permisos.

---

## 🛠️ Tecnologías Utilizadas

- **IDE utilizado:** IntelliJ IDEA 2024.1.1 (recomendado)  
- **Lenguaje de programación:** Java 17+  
- **Framework:** Spring Boot 3.x  
- **Gestor de dependencias:** Maven  
- **Base de datos:** MySQL 8.0+  
- **Migraciones:** Flyway  
- **Seguridad:** Spring Security con JWT  
- **Documentación:** SpringDoc OpenAPI (Swagger UI) 


---

## 🚀 Cómo correr el proyecto

### Requisitos previos

- Java 17 o superior instalado
- MySQL 8.0 o superior instalado y en ejecución
- Maven instalado
- IDE como IntelliJ IDEA, Eclipse o VSCode
- Cliente REST (Postman, Insomnia, curl) para probar endpoints

---

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/foroapi.git
cd foroapi
```

---

### 2. Crear la base de datos

En tu gestor MySQL, crea la base de datos vacía:

```sql
CREATE DATABASE foroapi_db;
```

> ⚠️ **No necesitas crear tablas ni insertar datos manualmente.** Flyway lo hará automáticamente al ejecutar el proyecto.

---

### 3. Configurar propiedades

Edita el archivo `src/main/resources/application.properties` con los datos de conexión de tu base:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foroapi_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

---

### 4. Ejecutar la aplicación

Desde tu IDE:

Ejecuta la clase principal (ej. ForoApiApplication.java)

El backend correrá en:
http://localhost:8080

---

### 5. Acceder a la documentación
La documentación OpenAPI está disponible en:

http://localhost:8080/swagger-ui.html


### 6. Autenticación y uso

-Registra usuarios con POST /usuarios/registro

-Loguea con POST /usuarios/login para obtener JWT

-Usa el token JWT en el header Authorization: Bearer <token> para llamadas autenticadas

-Solo autores pueden modificar o eliminar sus tópicos y respuestas

-Usuarios con rol ADMIN tienen permisos adicionales (ej. eliminar usuarios)

### 7. Control de acceso
Operaciones modificables por usuario solo si es autor

-Roles ADMIN tienen permisos especiales para gestionar usuarios

-JWT protege las rutas que lo requieren


## 8. 👤 Usuarios precargados (Flyway)

Flyway crea automáticamente los siguientes usuarios y roles al iniciar el proyecto:

| Rol           | Correo                      | Contraseña |
|---------------|-----------------------------|------------|
| ADMINISTRADOR | `admin@outlook.com`         | `123`      |
| USUARIO       | `user@outlook.com`          | `123`      |

---

## 9. Endpoints

| Método  | Endpoint                          | Descripción                     | Acceso               |
|---------|----------------------------------|--------------------------------|----------------------|
| POST    | `/usuarios/login`                 | Login y obtención de token JWT | Público              |
| POST    | `/usuarios/registro`              | Registrar nuevo usuario         | Público              |
| GET     | `/usuarios/me`                   | Obtener datos del usuario       | Usuario autenticado  |
| GET     | `/usuarios/{id}`                 | Obtener usuario por ID          | Usuario autenticado  |
| PATCH   | `/usuarios/{id}`                 | Actualizar usuario (solo propio)| Usuario autenticado  |
| PATCH   | `/usuarios/{id}/hacer-admin`     | Asignar rol ADMIN a usuario     | ADMIN                |
| DELETE  | `/usuarios/{id}`                 | Eliminar usuario (lógico)       | ADMIN                |

| Método  | Endpoint                          | Descripción                     | Acceso               |
|---------|----------------------------------|--------------------------------|----------------------|
| POST    | `/cursos`                        | Crear nuevo curso               | ADMIN                |
| GET     | `/cursos`                        | Listar cursos                  | Usuario autenticado  |
| GET     | `/cursos/{id}`                  | Obtener curso por ID           | Usuario autenticado  |
| PUT     | `/cursos/{id}`                  | Actualizar curso               | ADMIN                |
| DELETE  | `/cursos/{id}`                  | Eliminar curso                 | ADMIN                |

| Método  | Endpoint                          | Descripción                     | Acceso               |
|---------|----------------------------------|--------------------------------|----------------------|
| POST    | `/cursos/{idCurso}/topicos`      | Crear tópico en curso           | Usuario autenticado  |
| GET     | `/cursos/{idCurso}/topicos`      | Listar tópicos por curso        | Usuario autenticado  |
| GET     | `/topicos`                      | Listar todos los tópicos        | Usuario autenticado  |
| GET     | `/topicos/{id}`                | Obtener tópico por ID           | Usuario autenticado  |
| PATCH   | `/topicos/{id}`                | Actualizar tópico (solo autor)  | Autor del tópico     |
| DELETE  | `/topicos/{id}`                | Eliminar tópico (solo autor)    | Autor del tópico     |
| PATCH   | `/topicos/{id}/cerrar`           | Cerrar tópico                   | ADMIN                |

| Método  | Endpoint                          | Descripción                     | Acceso               |
|---------|----------------------------------|--------------------------------|----------------------|
| POST    | `/topicos/{idTopico}/respuestas` | Crear respuesta en tópico       | Usuario autenticado  |
| GET     | `/topicos/{idTopico}/respuestas` | Listar respuestas por tópico    | Usuario autenticado  |
| GET     | `/respuestas/{id}`              | Obtener respuesta por ID        | Usuario autenticado  |
| PATCH   | `/respuestas/{id}`              | Actualizar respuesta (autor)    | Autor de la respuesta|
| DELETE  | `/respuestas/{id}`              | Eliminar respuesta (autor)      | Autor de la respuesta|


## 🧑‍💻 Autor

Desarrollado por Ricardo Henaine

