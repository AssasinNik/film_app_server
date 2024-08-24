# Project: FilmAPI Server

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-blue)
![Ktor](https://img.shields.io/badge/Ktor-1.6.8-blue)
![HikariCP](https://img.shields.io/badge/HikariCP-5.0.1-blue)
![Jackson](https://img.shields.io/badge/Jackson-Enabled-green)
![SSL](https://img.shields.io/badge/SSL-Enabled-green)
![JWT](https://img.shields.io/badge/JWT-Enabled-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Enabled-green)

## Описание
Этот проект представляет собой сервер API, написанный на Kotlin с использованием Ktor, предназначенный для работы в проекте Samsung Innovation Campus. Сервер позволяет пользователям регистрироваться, аутентифицироваться, загружать изображения, искать новинки фильмов и находить фильмы по жанрам.

## Основные технологии
- **Kotlin + Ktor**: Базовая структура сервера.
- **HikariCP**: Управление пулом соединений с базой данных PostgreSQL.
- **PostgreSQL**: Основная база данных для хранения данных о пользователях, фильмах и других связанных объектах.
- **SSL**: Шифрование соединений для безопасности.
- **JWT (JSON Web Tokens)**: Используется для аутентификации и авторизации пользователей.
- **Argon2**: Алгоритм хеширования паролей для обеспечения безопасности.
- **Multipart**: Поддержка загрузки изображений в API.
- **Jackson**: Для сериализации/десериализации данных в JSON.

## Функциональность API
- **Регистрация пользователей**: Позволяет новым пользователям создавать учетные записи.
- **Аутентификация**: Позволяет пользователям входить в систему с использованием JWT или с помощью Пароля и Почты.
- **Загрузка изображений**: Поддержка загрузки изображений (например, для обложек фильмов).
- **Поиск новинок среди фильмов**: Возвращает список недавно добавленных фильмов.
- **Поиск фильмов по жанрам**: Позволяет искать фильмы по заданным жанрам.

## Начало работы
### Требования
- Kotlin 1.5.21 или выше
- Gradle 6.8 или выше
- JDK 11 или выше

### Установка
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/AssasinNik/film_app_server.git
   cd filmapi-server
   ```

2. Настройте базу данных PostgreSQL:
   - Создайте базу данных для приложения.
   - Создайте пользователя и назначьте ему права на базу данных.
   - Обновите файл `Database.kt` с данными о базе данных.

3. Сконфигурируйте SSL (если используется):
   - Убедитесь, что у вас есть сертификат и ключ SSL.
   - Обновите конфигурацию Ktor, чтобы использовать SSL.

### Сборка и запуск
1. Сборка проекта:
   ```bash
   ./gradlew build
   ```

2. Запуск сервера:
   ```bash
   ./gradlew run
   ```

3. Сервер будет запущен на порту, указанном в конфигурации. По умолчанию это порт `8080`.

## Тестирование API
Рекомендуется использовать инструменты для тестирования API, такие как Postman или cURL.
Вот пример запроса POST для регистрации пользователя на сервере, который возвращает ответ с данными о зарегистрированном пользователе и токеном аутентификации:

### Запрос
```http
POST https://film-app-server.com/auth/register
Content-Type: application/json

{
    "username": "Илья",
    "email": "ilya1.bystrikov@gmail.com",
    "parol_user": "Parol1810!"
}
```

### Ответ
```json
{
    "data": {
        "id": 2,
        "username": "Илья",
        "email": "ilya1.bystrikov1@gmail.com",
        "parol": null,
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaWxtX2FwcCIsImF1ZCI6ImZpbG1fYXBwIiwiZW1haWwiOiJpbHlhMS5ieXN0cmlrb3YxQGdtYWlsLmNvbSJ9.VaZR1ZuCPSDyh3Gm5kUhpIED1NpkGX8eagHRk65T0l0",
        "image": null
    },
    "message": null,
    "statusCode": {
        "value": 200,
        "description": "OK"
    }
}
```

### Объяснение
- Запрос отправляется методом `POST` на endpoint `https://film-app-server.com/auth/register`.
- В теле запроса передаются необходимые данные в формате JSON: имя пользователя, электронная почта и пароль.
- Ответ от сервера содержит:
  - Объект `data` с информацией о зарегистрированном пользователе, включая идентификатор, имя пользователя, электронную почту и токен аутентификации.
  - Статусный код 200 и описание "OK", подтверждающие успешное выполнение запроса.
Вот пример запроса POST на endpoint, который возвращает список новинок фильмов на основе токена аутентификации:

### Запрос
```http
POST https://film-app-server.com/films/new
Content-Type: application/json

{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaWxtX2FwcCIsImF1ZCI6ImZpbG1fYXBwIiwiZW1haWwiOiJpbHlhMS5ieXN0cmlrb3ZAZ21haWwuY29tIn0.ZyEgIm7DtbdvArY6JNH_GyFkE3F8G_dUTcwgpfxhFHk"
}
```

### Ответ
```json
{
    "data": [
        {
            "movie_id": 27293,
            "title": "Chien et chat",
            "titleEn": "Chien et chat",
            "genres": [
                "комедия",
                "приключения"
            ],
            "posterURL": "https://image.openmoviedb.com/kinopoisk-images/4483445/4f426122-87e9-46f8-b775-f64f0e44c8d3/orig",
            "rating": 6.445,
            "length": 86,
            "description": "Коты и собаки редко ладят. Но этой парочке придется объединиться, чтобы вернуться к хозяевам и привычной жизни домашних питомцев. По нелепой случайности они потерялись в аэропорту, и теперь «лучшим врагам» предстоит опасное, но захватывающее путешествие. А тем временем их хозяева — звезда социальных сетей и профессиональный вор — объединятся, чтобы найти своих любимцев.",
            "releaseDate": 2024,
            "ageLimit": 12
        },
        {
            "movie_id": 30211,
            "title": "Red Right Hand",
            "titleEn": "Red Right Hand",
            "genres": [
                "боевик",
                "триллер"
            ],
            "posterURL": "https://image.openmoviedb.com/kinopoisk-images/10592371/ff7ffa5b-4bc5-455c-b66e-00ef3321c924/orig",
            "rating": 6.56,
            "length": 111,
            "description": "Кэш живет скромной и мирной жизнью, помогая мужу покойной сестры воспитывать племянницу Саванну и управляться с фермой в небольшом городишке в Аппалачах. Но когда Кошка, глава местной банды и садистка, начинает угрожать его семье, Кэш понимает, что готов на всё ради их защиты. И чем дольше продолжается противостояние, тем сильнее стирается граница между добром и злом.",
            "releaseDate": 2024,
            "ageLimit": 18
        }
    ],
    "statusCode": {
        "value": 200,
        "description": "OK"
    }
}
```

### Объяснение
- Запрос отправляется методом `POST` на endpoint `https://film-app-server.com/films/new`.
- В теле запроса передается токен JWT для аутентификации.
- Ответ содержит объект `data`, который включает массив с информацией о фильмах, таких как `movie_id`, `title`, `genres`, `posterURL`, `rating`, `length`, `description`, `releaseDate` и `ageLimit`.
- Статусный код `200` с описанием "OK" подтверждает успешное выполнение запроса.
