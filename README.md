# 🌤️ Smart Weather Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Render](https://img.shields.io/badge/Deployed_on-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)

Backend robusto para la aplicación Smart Weather. Este servicio actúa como orquestador, consumiendo datos meteorológicos en tiempo real de Open-Meteo y enriqueciéndolos con recomendaciones inteligentes generadas por AI en Google Gemini.

## 📸 Demo
Próximamente

## 🚀 Características Principales

* **API RESTful:** Endpoints claros para búsqueda de ciudades, obtención de clima actual/pronóstico y sugerencias de IA.
* **Integración con IA:** El Smart Assistant utiliza Google Gemini para generar consejos de outfit y actividades basados en el clima.
* **Arquitectura en Capas:** Controladores, Servicios y DTOs bien separados.
* **Dockerizado:** Listo para desplegar en cualquier contenedor (optimizado para Render).
* **Keep-Alive:** Endpoint `/health` ligero para monitorización de uptime.

## 🛠️ Tech Stack

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.5.7
* **Build Tool:** Maven
* **IA:** Google Gemini API
* **Datos Clima:** Open-Meteo API
* **Despliegue:** Docker & Render (Free Tier)

## ⚙️ Instalación y Configuración Local

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/isaac-diez/weather-app-back.git](https://github.com/isaac-diez/weather-app-back.git)
    cd weather-app-backend
    ```

2.  **Variables de Entorno:**
    Crea un archivo o configura en tu IDE las siguientes variables (o edita `application.properties` para desarrollo):
    ```properties
    GEMINI_API_KEY=tu_api_key_aqui
    ```

3.  **Compilar y Ejecutar:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4.  **Probar:**
    El servidor iniciará en `http://localhost:8080`.

## 🐳 Docker (Producción)

El proyecto incluye un `Dockerfile` optimizado para limitar el uso de memoria RAM para Render.

```bash
# Construir la imagen
docker build -t weather-backend .

# Correr el contenedor
docker run -p 8080:8080 -e OPENWEATHER_API_KEY=xxx -e GEMINI_API_KEY=yyy weather-backend
```

## 📡 API Endpoints

|Método|Endpoint|Descripción|
|---|---|---|
|GET|/api/weather/cities?name={nombre}|Busca ciudades por nombre.|
|POST|/api/weather/current|Obtiene clima actual + IA insights.|
|POST|/api/weather/forecast|Obtiene previsión a 5 días.|
|GET|/api/weather/health|Health Check (usado por Cronjobs).|


## ⚠️ Nota sobre Render (Cold Starts)
Este servicio está alojado en Render. Tras 15 minutos de inactividad, el servicio entra en suspensión.

Consecuencia: La primera petición tras un periodo de inactividad puede tardar 30-50 segundos.

Solución: Se ha implementado un endpoint /health que puede ser llamado por un servicio externo (como Cron-job.org) cada 10 minutos para evitar el apagado.


## 🗺️ Roadmap
[ ] Implementar soporte multi-idioma (i18n) en los prompts de Gemini.
[ ] Añadir pronóstico horario
[ ] Añadir caché (Redis) para reducir llamadas a APIs externas.

## 🤝 Contribución
Las Pull Requests son bienvenidas. Para cambios mayores, por favor abre primero un issue para discutir lo que te gustaría cambiar.