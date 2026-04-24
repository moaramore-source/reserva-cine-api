# Reserva Cine API

API REST desarrollada con Spring Boot para la gestión de eventos de cine y reservas.

---

## Tecnologías

* Java 21
* Spring Boot 3
* Spring Security + JWT
* Hibernate / JPA
* MySQL
* Maven

---

## Configuración

### 1. Base de datos

Ejecutar el script:

```
database_reserva_cine.sql
```

Esto crea la base de datos:

```
reserva_cine_db
```

---

### 2. Configurar credenciales

Editar:

```
src/main/resources/application.properties
```

Y completar:

```
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
jwt.secret=TU_CLAVE_SECRETA
```

---

### 3. Ejecutar la API

```
mvn spring-boot:run
```

Disponible en:

```
http://localhost:8086
```

---

## Autenticación

### Login

```
POST /api/auth/login
```

Ejemplo:

```
{
  "username": "admin",
  "password": "admin123"
}
```

Respuesta:

```
{
  "token": "..."
}
```

Usar en headers:

```
Authorization: Bearer TU_TOKEN
```

---

## Endpoints

### Públicos

* Login

### Protegidos

* Eventos
* Reservas
* Administración

---

## Usuarios de prueba

* admin / admin123
* cliente1 / cliente123
* cliente2 / cliente123

---



* No se incluyen credenciales reales por seguridad.
* Proyecto preparado para entorno local.
