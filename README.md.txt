# 🏆 Sistema de Gestión - Liga Pokémon
> 🚀 *Plataforma distribuida para la centralización, automatización y administración del ecosistema de la Liga Pokémon.*

---

## 📝 Contexto

### 🔍 Descripción del Dominio del Problema
La administración de un entorno masivo como la Liga Pokémon requiere el flujo constante de información sensible entre diferentes entidades: registros médicos en clínicas, control biológico en enciclopedias digitales, transacciones comerciales en tiendas, control de stock de insumos y el seguimiento de competidores en torneos oficiales. 

Desplegar esto bajo una arquitectura monolítica tradicional genera un fuerte acoplamiento, cuellos de botella en la base de datos y fallos en cadena que inhabilitarían todo el sistema durante temporadas de alta demanda de combates.

### 💡 Solución del Proyecto
Se diseñó e implementó una solución distribuida basada en **Microservicios** utilizando el stack de **Spring Cloud** y **Java 17+**. Cada dominio crítico funciona como un servicio independiente y desacoplado con su propio ciclo de vida y almacenamiento de datos. 

Las comunicaciones se unifican mediante un **API Gateway** reactivo que centraliza el enrutamiento y la seguridad de las peticiones, mientras que un **Servidor de Descubrimiento (Eureka)** automatiza la localización dinámica de las instancias tanto en entornos locales como contenerizados.

---

## 👥 Créditos
* 🧑‍💻 **[Miguel Angel Aravena Galdames]**
* 🧑‍💻 **[Vicente Abraham Vilchez ruz]**
* 🧑‍💻 **[Daniel Ignacio Coppia Nelson]**

---

## 🏗️ Arquitectura del Sistema
El ecosistema está compuesto de forma modular por los siguientes componentes detallados:

* **🌐 `eureka-server` (Puerto 8761):** Servidor de descubrimiento que actúa como el directorio central para que todas las piezas de la arquitectura se registren y localicen dinámicamente sin usar IPs fijas.
* **🛡️ `api-gateway` (Puerto 8080):** Punto de entrada único del cliente. Resuelve el enrutamiento reactivo hacia los microservicios y unifica la interfaz visual de la documentación técnica.
* **🔑 `acceso_seguridad`:** Módulo encargado del control de accesos, roles del sistema (`ADMIN`, `OPERADOR`, `ENTRENADOR`) y encriptación de credenciales.
* **🎒 `entrenadores`:** Gestión del perfil del entrenador, rangos de competencia y regiones de origen.
* **🏥 `centropokemon`:** Administración de admisiones a enfermería, estados de salud y atenciones médicas Pokémon.
* **📦 `inventario`:** Control de stock e insumos esenciales como Pokéballs, consumibles y pociones de combate.
* **🏪 `tienda`:** Registro de catálogos de venta, precios y procesamiento de transacciones comerciales.
* **📱 `pokedex`:** Registro biológico, enciclopedia digital y categorización de especies Pokémon.
* **🏅 `medallas`:** Control de gimnasios oficiales e historial de insignias obtenidas por competidor.
* **⚔️ `torneos`:** Organización de llaves competitivas, emparejamientos automáticos e historial de campeonatos.

---

## 🌐 Networking (Rutas del API Gateway)

Cualquier petición externa o desde una aplicación cliente debe direccionarse exclusivamente a través del API Gateway (`http://localhost:8080`). Las rutas principales expuestas son:

| 🧩 Microservicio | 🛣️ Ruta Base en Gateway | 🛠️ Operación Destacada |
| :--- | :--- | :--- |
| **Acceso Seguridad** | `/api/v1/acceso-seguridad/**` | `POST /` (Registro y Autenticación) |
| **Centro Pokémon** | `/api/v1/centropokemons/**` | `GET /` (Listar clínicas activas) |
| **Entrenadores** | `/api/v1/entrenadores/**` | `GET /{id}` (Consultar perfil de entrenador) |
| **Inventario** | `/api/v1/inventarios/**` | `PUT /{id}` (Actualizar existencias de ítems) |
| **Medallas** | `/api/v1/medallas/**` | `POST /` (Asignar insignias de gimnasio) |
| **Pokedex** | `/api/v1/pokedex/**` | `GET /` (Consultar enciclopedia de especies) |
| **Tienda** | `/api/v1/tiendas/**` | `POST /` (Procesar transacciones de compra) |
| **Torneos** | `/api/v1/torneos/**` | `DELETE /{id}` (Dar de baja una llave de torneo) |

---

## 🔌 Accesos (Documentación Swagger)

La especificación OpenAPI y la interfaz interactiva de pruebas de todos los componentes están centralizadas y expuestas de manera unificada a través del API Gateway en la siguiente dirección:

* 🔗 **Swagger UI (Entorno Local):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

> ℹ️ **Nota de Uso:** Una vez dentro de la interfaz web, use el selector desplegable ubicado en la esquina superior derecha (**"Select a definition"**) para alternar entre la documentación interactiva de cada microservicio registrado en la red.

---

## 🚀 Guía de Despliegue

### 📋 Requisitos Mínimos
* **Java Development Kit (JDK):** Versión 17 o superior.
* **Apache Maven:** Versión 3.8+ o uso del Maven Wrapper (`./mvnw`).
* **Docker:** Docker Desktop y Docker Compose instalados y activos.

---

### 💻 Opción A: Ejecución en Entorno Local / Híbrido (Terminal o IDE)

Para levantar el proyecto de forma nativa en su máquina, los servicios deben iniciarse **estrictamente en orden secuencial** para evitar fallos de conectividad con el servidor de descubrimiento:

1.  **Levantar el Servidor de Descubrimiento:**
    Navegue a la carpeta raíz de `eureka-server` y ejecute:
    ```bash
    ./mvnw spring-boot:run
    ```
    *Asegúrese de que la consola indique que el servidor está completamente activo en `http://localhost:8761

### Base de Datos
CREATE DATABASE IF NOT EXISTS bd_seguridad;
CREATE DATABASE IF NOT EXISTS bd_entrenadores;
CREATE DATABASE IF NOT EXISTS bd_pokedex;
CREATE DATABASE IF NOT EXISTS bd_torneos;
CREATE DATABASE IF NOT EXISTS bd_tienda;
CREATE DATABASE IF NOT EXISTS bd_medallas;
CREATE DATABASE IF NOT EXISTS bd_inventario;
CREATE DATABASE IF NOT EXISTS bd_centropk;
