# Tripify

Монорепозиторий бэкенда Tripify — набор микросервисов на **Spring Boot** и **Java 21**, собранных в Gradle composite build. Каждый сервис — отдельный Gradle-проект со своим API, тестами и (где есть) Postman-коллекцией.

---

## Требования

| Инструмент | Версия / примечание |
|---|---|
| [Colima](https://github.com/abiosoft/colima) | Docker runtime для macOS |
| Docker Compose | v2 |
| Java | 21 (Temurin / JDK) |
| Postman | для ручного тестирования API |

---

## Быстрый старт (локально)

### 1. Запустить Colima

Рекомендуемые ресурсы для сборки всех сервисов:

```bash
colima start --cpu=8 --memory=16 --disk=150
```

Проверка:

```bash
docker info
```

### 2. Поднять инфраструктуру и сервисы

Из корня репозитория:

```bash
cd docker/local
docker compose up --build -d
```

Первый запуск займёт несколько минут — собираются Docker-образы всех сервисов.

Проверка статуса:

```bash
docker compose ps
```

Остановка:

```bash
docker compose down
```

Полная очистка с томами:

```bash
docker compose down -v
```

---

## Что поднимается

### Микросервисы

| Сервис | Порт (host) | Назначение |
|---|---:|---|
| **auth-service** | 8080 | OTP-авторизация, JWT в HttpOnly cookies |
| **hotels-parser** | 8081 | Парсинг отелей → Kafka |
| **notification-service** | 8082 | Уведомления (Kafka consumer) |
| **users-service** | 8083 | Профили пользователей |
| **hotels-service** | 8084 | Поиск и каталог отелей |
| **tickets-service** | 8086 | Поиск авиабилетов |
| **info-service** | 8087 | Справочник стран, курсы, погода |
| **pack-service** | 8088 | Туристические пакеты (сборка поездки) |

### Инфраструктура

| Сервис | Порт | UI / доступ |
|---|---:|---|
| PostgreSQL | 5432 | `tripify` / `tripify`, БД `tripify_db` |
| Redis | 6379 | — |
| MongoDB | 27017 | — |
| Kafka | 9092 | — |
| **Kafka UI** | 8090 | http://localhost:8090 |
| **Mongo Express** | 8085 | http://localhost:8085 |
| **Kong** (API Gateway) | 8000 | прокси: http://localhost:8000 |

Kong маршрутизирует публичные префиксы (`/auth`, `/hotels`, `/tickets`, `/users`, `/v1/countries`, `/v1/admin`, `/v1/packs`). Для отладки удобнее ходить **напрямую в сервис по его порту** — так проще смотреть логи конкретного контейнера.

---

## Структура репозитория

```
tripify/
├── auth-service/
├── hotels-parser/
├── hotels-service/
├── info-service/
├── notification-service/
├── pack-service/
├── tickets-service/
├── users-service/
├── docker/
│   ├── local/          # локальный docker-compose
│   ├── prod/           # prod-окружение
│   └── kong/           # конфиг API Gateway
├── settings.gradle.kts # Gradle composite (все сервисы)
└── build.gradle.kts    # задача buildAll
```

### Gradle (без Docker)

```bash
./gradlew projects          # список included builds
./gradlew buildAll          # собрать все сервисы
./gradlew :tickets-service:build   # один сервис
```

### IntelliJ IDEA

Открывайте **корень** `tripify` (не отдельный сервис). IDEA подхватит composite build из `settings.gradle.kts` и покажет каждый сервис отдельным Gradle-модулем.

---

## Postman

В нескольких сервисах лежат готовые коллекции и environment-файлы — их можно импортировать в Postman и сразу дергать API на локальном Docker.

### Как пользоваться

1. **Import** → выберите `*.postman_collection.json` из папки `postman/` нужного сервиса.
2. **Import** → выберите `*.postman_environment.json` с суффиксом `local` или `docker` (если есть).
3. В правом верхнем углу Postman выберите импортированный **Environment**.
4. Запускайте запросы **сверху вниз** внутри папок — многие коллекции рассчитаны на цепочку (search → list → details), скрипты сохраняют `hotelId`, `tid`, `packId` и т.д. между шагами.
5. Для сервисов с обязательными заголовками (`X-Anonymous-Id`, `X-Request-Id`) значения уже заданы в environment — при необходимости поменяйте их на свои.

> **Совет:** если запросы идут в Docker, убедитесь что `baseUrl` в environment указывает на **host-порт** из таблицы выше, а не на `8080` по умолчанию (у hotels/tickets в коллекции дефолт — bootRun).

---

### auth-service

| Файл | Путь |
|---|---|
| Коллекция | `auth-service/postman/auth-service-cookie-auth.postman_collection.json` |

**Переменные коллекции:** `baseUrl` = `http://localhost:8080`, `phone_number`, `otp_code`.

**Flow:** Request OTP → Verify OTP → Refresh → Logout.

Postman автоматически сохраняет и отправляет **HttpOnly cookies** (`access_token`, `refresh_token`). Для локального HTTP в `application.yml` может понадобиться `security.cookies.secure=false` — иначе Secure-cookies не сохранятся.

---

### hotels-service

| Файл | Путь |
|---|---|
| Коллекция | `hotels-service/postman/Tripify-Hotels-Service.postman_collection.json` |

**`baseUrl` для Docker:** `http://localhost:8084` (в коллекции по умолчанию `8080` — поменяйте в Variables).

**Flow:** Get Hotel Filters → Search Hotel → Get Hotels → Get Hotels (next page) → Get Hotel By Id.

Обязательные заголовки: `X-Anonymous-Id`, `X-Generation-Id`, `X-Request-Id` (+ `X-Hotels-Revision` на search/details).

---

### tickets-service

| Файл | Путь |
|---|---|
| Коллекция | `tickets-service/postman/Tripify-Tickets-Service.postman_collection.json` |
| Environment (Docker) | `tickets-service/postman/Tripify-Tickets-Service.docker.postman_environment.json` |
| Environment (bootRun) | `tickets-service/postman/Tripify-Tickets-Service.local.postman_environment.json` |

**`baseUrl` для Docker:** `http://localhost:8086` — уже в `.docker.postman_environment.json`.

**Flow:** Get Ticket Filters → Search Ticket → Search Tickets → Get Ticket by ID.

---

### info-service

| Файл | Путь |
|---|---|
| Коллекция | `info-service/postman/info-service.postman_collection.json` |
| Environment (Docker) | `info-service/postman/info-service.local.postman_environment.json` |
| Environment (bootRun) | `info-service/postman/info-service.bootrun.postman_environment.json` |

**`baseUrl` для Docker:** `http://localhost:8087`.

**Flow:** Admin Refresh Country → Get Country Info.

Поддерживаемые страны (ISO alpha-2): `RU`, `CN`, `AE`, `TR`, `TH`, `ES`, `IT`, `FR`, `JP`, `EG`.

Refresh одной страны из консоли:

```bash
curl -sS -X POST "http://localhost:8087/v1/admin/countries/CN/refresh" \
  -H "X-Anonymous-Id: admin-cli-001" \
  -H "X-Request-Id: req-$(date +%s)"
```

---

### pack-service

| Файл | Путь |
|---|---|
| Коллекция | `pack-service/postman/pack-service.postman_collection.json` |
| Environment (local) | `pack-service/postman/pack-service.local.postman_environment.json` |

**`baseUrl` для Docker:** `http://localhost:8088`.

**Эндпоинты:** список паков, popular, детали по `packId`, история просмотров.

Swagger UI: http://localhost:8088/swagger-ui.html

---

### Сервисы без Postman

| Сервис | Как тестировать |
|---|---|
| **users-service** (:8083) | Swagger / OpenAPI в сервисе, либо через Kong |
| **notification-service** (:8082) | Kafka consumer — через события из других сервисов |
| **hotels-parser** (:8081) | Запускается вместе со стеком, публикует в Kafka topic `hotels.parsed` |

---

## Полезные команды

```bash
# Логи одного сервиса
docker compose -f docker/local/docker-compose.yaml logs -f hotels-service

# Пересобрать один сервис
docker compose up --build -d hotels-service

# Healthcheck Kong
curl -s http://localhost:8001/status | jq .
```

---

## Prod

Prod-окружение: `docker/prod/docker-compose.yaml` (+ Caddy, внешние порты могут отличаться).

---

## Лицензия

Внутренний проект Tripify.
