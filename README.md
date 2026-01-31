# 📦 Gestión de Proyectos – Backend Spring Boot

Este proyecto corresponde al **backend desarrollado en Spring Boot** para la gestión de **Asesorías** y **Disponibilidades**, como parte de un sistema académico que integra **múltiples servidores RESTful** consumidos por una aplicación **Angular**.

El sistema está diseñado para demostrar el dominio de **diferentes arquitecturas backend**, todas conectadas a una **misma base de datos PostgreSQL**.

---

## 🧩 Arquitectura General del Proyecto

El sistema completo está compuesto por **tres servidores REST independientes**:

| Tecnología        | Entidades gestionadas            | Puerto |
|------------------|----------------------------------|--------|
| Jakarta EE       | Programadores, Proyectos         | 8080   |
| **Spring Boot**  | **Asesorías, Disponibilidades**  | **8081** |
| FastAPI (Python) | Usuarios                         | 8002   |

Este repositorio corresponde **exclusivamente al backend Spring Boot**.

---

## 🛠️ Tecnologías Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- RESTful API

---

## 🗂️ Estructura del Proyecto

```
src/main/java
└── ec.edu.ups.ppw.GestorProyectos
    ├── DAO           # Repositorios (Spring Data JPA)
    ├── bussines      # Lógica de negocio
    ├── modelo        # Entidades JPA
    ├── services      # Controladores REST
    └── GestionProyectosSpringApplication.java
```

---

## 🧱 Entidades Gestionadas

### 📌 Asesoría
Representa una solicitud de asesoría realizada por un cliente hacia un programador.

**Campos principales:**
- Nombre y correo del cliente
- Fecha y hora de la asesoría
- Descripción del proyecto
- Estado de la solicitud
- Mensaje de respuesta
- Relación lógica con Programador (mediante ID)

---

### 📌 Disponibilidad
Representa los horarios disponibles de un programador.

**Campos principales:**
- Tipo (recurrente / puntual / bloqueo)
- Modalidad (virtual / presencial)
- Día de la semana o fecha específica
- Hora de inicio y fin
- Relación lógica con Programador (mediante ID)

---

## 🔗 Endpoints REST

### 🔹 Asesorías

- `GET    /api/asesorias`
- `GET    /api/asesorias/{id}`
- `POST   /api/asesorias`
- `PUT    /api/asesorias/{id}`
- `DELETE /api/asesorias/{id}`

---

### 🔹 Disponibilidades

- `GET    /api/disponibilidades`
- `GET    /api/disponibilidades/{id}`
- `POST   /api/disponibilidades`
- `PUT    /api/disponibilidades/{id}`
- `DELETE /api/disponibilidades/{id}`

---

## ⚙️ Configuración de la Aplicación

Archivo: `application.properties`

```properties
spring.application.name=GestionProyectos_Spring

spring.datasource.url=jdbc:postgresql://localhost:5432/ProyectoPortafolios_bd
spring.datasource.username=proyectoportafolio_usr
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8081
```

---

## ▶️ Ejecución del Proyecto

### Desde el IDE
Ejecutar la clase:
```
GestionProyectosSpringApplication.java
```

### Desde consola
```bash
mvn spring-boot:run
```

La aplicación quedará disponible en:
```
http://localhost:8081
```

---

## 🔍 Pruebas

Los endpoints pueden ser probados mediante:
- Postman
- Navegador web (GET)
- Frontend Angular

Ejemplo:
```
GET http://localhost:8081/api/asesorias
```

---

## 🎯 Objetivo Académico

Este proyecto tiene como objetivo:

- Aplicar Spring Boot como backend REST
- Utilizar Spring Data JPA para la persistencia
- Integrar múltiples backends sobre una misma base de datos
- Facilitar el consumo desde un frontend Angular
- Comparar enfoques entre Jakarta EE, Spring Boot y FastAPI

---

## ✍️ Autor
**Carlos Moyano**
Proyecto desarrollado con fines académicos

Carrera: **Ingeniería en Ciencias de la Computación**  
Materia: ¨**Programación y Plataformas Web**

