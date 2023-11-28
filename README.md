# Описание курсовой работы
__Фул стек приложение - справочник документов__

## Структура проекта

- `backend` - Бэкенд на java.
- `ui` - Фронтенд на react + redux.

## Подготовка к запуску приложения:

__Установите:__

- [node](https://nodejs.org) - front
- [openjdk](https://adoptopenjdk.net/releases.html?variant=openjdk15&jvmVariant=hotspot) 15 - java бэк
- [docker](https://docs.docker.com/engine/install/)
- [docker-compose](https://docs.docker.com/compose/install/)

## Запуск приложения через docker:

```
./gradlew backend:bootJar
```

```
./gradlew ui:npm_run_build
```

### Переменные окружения:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/courseWork
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:29092
```

