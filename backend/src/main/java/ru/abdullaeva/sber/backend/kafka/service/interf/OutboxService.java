package ru.abdullaeva.sber.backend.kafka.service.interf;

import ru.abdullaeva.sber.backend.kafka.model.Outbox;

/**
 * Сервис, содержащий методы для работы с исходящими сообщениями
 */
public interface OutboxService {

    /**
     * Метод добавляет сообщение с информацией о созданном о документе в таблицу Outbox.
     * @param message - сообщение с информацией о документе.
     * @return - сохранённое сообщение.
     */
    Outbox writeMessageAtOutbox(Object message);

    /**
     * Метод отправляет в кафка сообщение из таблицы Outbox.
     * После отправки сообщения из таблицы удаляется.
     */
    void sendMessageFromOutbox();
}
