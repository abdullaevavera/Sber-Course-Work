# Описание курсовой работы
__Фул стек приложение - справочник документов__

## Структура проекта:

- `backend` - Бэкенд на java.
- `ui` - Фронтенд на react + redux.

## Подготовка к запуску приложения:

__Установите:__

- [node](https://nodejs.org) - front
- [openjdk](https://adoptopenjdk.net/releases.html?variant=openjdk15&jvmVariant=hotspot) 15 - java бэк
- [docker](https://docs.docker.com/engine/install/)
- [docker-compose](https://docs.docker.com/compose/install/)

## Запуск приложения с помощью docker:

```
./gradlew backend:bootJar
```

```
./gradlew ui:npm_run_build
```
```
docker-compose up
```

### Переменные окружения:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/courseWork
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

## Использование

__Адрес страницы:__ http://localhost:9000/#/

__Инструкция по обработке сообщений в кафка:__

_Документ отправляется на обработку в кафка в топик "inbox-mes" после обновления статуса документа
на статус "В процессе" / "IN_PROCESS". Когда сообщение появится в топике "inbox-mes", неоходимо 
отправить сообщение в кафка в топик "outbox-mes" с результатом обработки документа - или принять, 
или отклонить документ._

__Сообщение должно содержать:__

- `key` - ключ сообщения, отправленного в топик "inbox-mes".
- `value` - json с id документа и code-статусом "ACCEPTED" или "DECLINED"

__Пример тела сообщения в кафка (value):__

```
{ 
  "idDocument": 1,  
  "code": "ACCEPTED"
}
```
_После того, как сообщение будет отправлено из кафка, в таблице Inbox отобразится содержимое 
сообщения, при обновлении страницы статус обновится согласно результату обработки.
Прочитанные из таблицы Inbox сообщения помечаются как прочитанные и повторно не обрабатываются._