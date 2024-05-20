# PracticaFinalPAT
Práctica final de PAT de Santiago Arenas Martín, María Castilla Montes, Álvaro González Tabernero, Miguel Sánchez-Beato Gil.

# Planificación del Proyecto

### Definición del Roadmap:
El proyecto se planificó en dos iteraciones de dos semanas cada una, con un ciclo de "desarrollo ágil". Cada iteración tiene objetivos claro, priorizando las tareas esenciales para garantizar un avance constante y que sea medible.
Se eligieron Springs de dos semanas debido a la gran carga académica en el momento del curso en el que estabamos y siendo realistas de que habría semanas en las que no podríamos trabajar demasiado.
La planificación inicial esta hecha para que sea adaptable y flexible.

#### Iteración 1 (Semana 1 - Semana 2):
- **Objetivos:**
  - Desarrollo del backend para la gestión de médicos.
  - Implementación de las funcionalidades básicas de registro y login.
  - Dseño del futuru desarrollo del frontend para las funcionalidades implementadas.
- **Tareas:**
  - Backend:
    - Configurar el proyecto Spring Boot.
    - Implementar la API para registrar médicos.
    - Implementar la API para el login de médicos.
    - Diseñar el modelo de base de datos inicial.
    - Configurar GitHub Actions para la integración continua.
  - Frontend:
    - Configurar el proyecto de frontend.
    - Diseñar la interfaz de usuario para el registro y login.
    - Implementar la lógica de conexión con la API de backend.
    - Crear componentes reutilizables.
  - Documentación inicial del diseño y la arquitectura.

#### Iteración 2 (Semana 3 - Semana 4):
- **Objetivos:**
  - Desarrollo del backend para la gestión de conocimientos.
  - Implementación de las funcionalidades avanzadas de actualización y eliminación de médicos.
  - Refinamiento del sistema de CI/CD.
  - Desarrollo del frontend para las nuevas funcionalidades.
  - Documentación final y presentación.
- **Tareas:**
  - Backend:
    - Implementar la API para gestionar conocimientos.
    - Implementar la API para actualizar y eliminar la información de los médicos.
    - Mejorar la configuración de GitHub Actions para CI/CD.
    - Desarrollo e implementacion de Tests.
  - Frontend:
    - Diseñar la interfaz de usuario para la gestión de conocimientos.
    - Implementar la lógica de conexión con la API de backend para conocimientos.
    - Mejorar la experiencia de usuario y la usabilidad.
  - Preparar la presentación final del proyecto.

## 2. División de Tareas y Priorización: Backlog

### Backlog del Proyecto:
1. **Configuración del entorno de desarrollo (Alta prioridad)**
   - Configurar el proyecto Spring Boot.
   - Configurar el proyecto de frontend.
   - Configurar GitHub Actions para CI/CD.

2. **Implementación del registro de médicos (Alta prioridad)**
   - Diseñar el modelo de datos para los médicos.
   - Implementar el endpoint de registro de médicos.
   - Validar los datos de entrada para el registro.
   - Diseñar e implementar la interfaz de usuario para el registro.

3. **Implementación del login de médicos (Alta prioridad)**
   - Implementar el endpoint de login de médicos.
   - Generar y gestionar tokens de sesión.
   - Implementar la lógica de autenticación.
   - Diseñar e implementar la interfaz de usuario para el login.

4. **Gestión de conocimientos (Media prioridad)**
   - Diseñar el modelo de datos para los conocimientos.
   - Implementar el endpoint para agregar conocimientos.
   - Implementar el endpoint para actualizar conocimientos.
   - Implementar el endpoint para eliminar conocimientos.
   - Diseñar e implementar la interfaz de usuario para la gestión de conocimientos.

5. **Actualización de información de médicos (Media prioridad)**
   - Implementar el endpoint para actualizar la información de los médicos.
   - Validar los datos de entrada para la actualización.
   - Diseñar e implementar la interfaz de usuario para la actualización de médicos.

6. **Documentación y Presentación (Alta prioridad)**
   - Completar la documentación técnica y de usuario.
   - Preparar la presentación final del proyecto.

## 3. Objetivos en Cada Iteración: Tareas a Completar

### Iteración 1:
- Configuración completa del entorno de desarrollo.
- Implementación y prueba de las APIs de registro y login.
- Configuración básica de CI/CD con GitHub Actions.
- Diseño del modelo de base de datos inicial.
- Desarrollo del frontend para registro y login.
- Documentación técnica inicial.

### Iteración 2:
- Implementación y prueba de las APIs de gestión de conocimientos.
- Implementación y prueba de las APIs de actualización y eliminación de médicos.
- Mejora y refinamiento del sistema de CI/CD.
- Desarrollo del frontend para la gestión de conocimientos y actualización de médicos.
- Finalización de la documentación técnica y de usuario.
- Preparación de la presentación final del proyecto.

## 4. Asignación de Tareas entre los Miembros del Equipo y su Progreso

### Equipo:
- **Miguel: Backend**
  - **Responsabilidades:**
    - Desarrollo de servicios y entidades en Java. 
    - Implementar las APIs de registro, login y gestión de conocimientos. )
    - Diseñar el modelo de base de datos. 
    - Documentar la API y el modelo de datos. 
  - **Tareas:**
    - Configurar el proyecto Spring Boot. (COMPLETADO)
    - Implementar el endpoint de registro de médicos.(COMPLETADO)
    - Implementar el endpoint de login de médicos.(COMPLETADO)
    - Diseñar el modelo de datos para los médicos y conocimientos.(COMPLETADO)
    - Implementar el endpoint para agregar, actualizar y eliminar conocimientos.(COMPLETADO)

- **María: Tests y Planificación del Proyecto (QA)**
  - **Responsabilidades:**
    - Desarrollo de pruebas.
    - Planificación del proyecto.
    - Ayuda con la parte de JavaScript del frontend.
    - Configurar GitHub Actions para CI/CD.
  - **Tareas:**
    - Desarrollar pruebas unitarias y de integración. (COMPLETADO)
    - Planificar y documentar las iteraciones del proyecto. (COMPLETADO)
    - Ayudar con la implementación de la lógica de frontend en JavaScript.(COMPLETADO-AYUDA EXTRA DE MIGUEL)

- **Álvaro: Frontend**
  - **Responsabilidades:**
    - Desarrollo del frontend.
    - Diseño de la interfaz de usuario.
    - Documentación técnica del proyecto
  - **Tareas:**
    - Configurar el proyecto de frontend. (COMPLETADO)
    - Diseñar e implementar la interfaz de usuario para el registro, login y gestión de conocimientos. (COMPLETADO)
    - Conectar el frontend con la API de backend. (COMPLETADO)
    - Mejorar la experiencia de usuario y la usabilidad. (COMPLETADO)

- **Santiago: Frontend**
  - **Responsabilidades:**
    - Desarrollo del frontend.
    - Implementación de la lógica de conexión con la API.
    - Documentación técnica del proyecto
  - **Tareas:**
    - Configurar el proyecto de frontend. (COMPLETADO)
    - Diseñar e implementar la interfaz de usuario para el registro, login y gestión de conocimientos. (COMPLETADO)
    - Implementar la lógica de conexión con la API de backend.(COMPLETADO)

