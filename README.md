# Card Management System

## Описание

Card Management System — это приложение для управления картами, позволяющее пользователям регистрироваться, аутентифицироваться, создавать и управлять картами, а также выполнять переводы средств между картами.

## Технологии

- **Java**: 17
- **Spring Boot**: 3.4.5
- **Spring Data JPA**: 3.4.5
- **Spring Security**: 3.4.5
- **PostgreSQL**
- **Liquibase**

## Установка

### 1. Клонирование репозитория

Сначала клонируйте репозиторий на свой локальный компьютер:

```bash
git clone <URL_вашего_репозитория>
cd CardManagmentSystem
2. Установка зависимостей
Убедитесь, что у вас установлен Maven. Если он не установлен, следуйте инструкциям на официальном сайте Maven.

3. Настройка базы данных
Установите и запустите PostgreSQL.
Создайте новую базу данных для вашего приложения.
Настройте параметры подключения к базе данных в файле src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/ваша_база_данных
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
4. Запуск приложения
Локальный запуск
Для запуска приложения выполните следующие команды:
mvn clean install
mvn spring-boot:run
Запуск с помощью Docker
Если вы хотите запустить приложение с помощью Docker, убедитесь, что у вас установлен Docker и Docker Compose. Затем выполните следующую команду:
docker-compose up --build
Использование API
После запуска приложения вы можете использовать следующие конечные точки API:
Регистрация пользователя: POST /api/auth/register
Аутентификация пользователя: POST /api/auth/login
Создание карты: POST /api/cards
Получение списка карт: GET /api/cards
Получение карты по ID: GET /api/cards/{id}
Обновление карты: PUT /api/cards/{id}
Удаление карты: DELETE /api/cards/{id}
Блокировка карты: PUT /api/cards/{id}/block
Активация карты: PUT /api/cards/{id}/activate
Перевод средств: POST /api/cards/{fromId}/transfer/{toId}
Тестирование
Для запуска тестов используйте команду:
mvn test

