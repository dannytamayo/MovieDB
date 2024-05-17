# MovieCatalogApp

## Descripción
MovieCatalogApp es una aplicación desarrollada en Kotlin que permite a los usuarios explorar un amplio catálogo de películas. Los usuarios pueden calificar las películas y guardar sus favoritas en un dashboard personalizado.

## Funcionalidades
- **Explorar Películas**: Navega a través de un extenso catálogo de películas.
- **Calificar Películas**: Los usuarios pueden proporcionar su calificación para cada película.
- **Dashboard Personalizado**: Guarda tus películas favoritas y calificaciones en un tablero personalizado para acceder fácilmente en el futuro.

## Tecnologías Utilizadas
- **Kotlin**: Lenguaje principal de programación.
- **Android Studio**: Entorno de desarrollo integrado (IDE) para el desarrollo de la app.
- **SQLite**: Base de datos SQL utilizada para almacenar datos de usuarios y películas en el dispositivo.

## Instalación
Para instalar esta aplicación, sigue estos pasos:
1. Clona este repositorio usando `git clone [URL del repositorio]`.
2. Abre el proyecto en Android Studio.
3. Configura SQLite en el proyecto para manejar la base de datos:
   - **Crear tabla `users`**: Para almacenar información de los usuarios.
   - **Crear tabla `movies`**: Para almacenar información sobre las películas y las calificaciones.
4. Ejecuta la aplicación en un emulador o dispositivo Android.

## Configuración de la Base de Datos
Asegúrate de configurar la base de datos SQLite con las siguientes tablas:
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL
);

CREATE TABLE movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    rating REAL
);
