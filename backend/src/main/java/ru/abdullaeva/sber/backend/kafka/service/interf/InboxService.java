package ru.abdullaeva.sber.backend.kafka.service.interf;

import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;

import java.util.List;
import java.util.Set;

/**
 * Сервис, содержащий методы для работы со входящими сообщениями
 */
public interface InboxService {

    /**
     * Метод добавляет сообщение, полученное из кафка, в таблицу Inbox.
     * Сообщение будет добавлено в таблицу только если сообщения было получено
     * с уникальным ключом.
     * @param resultDto - пришедее из кафка содержимое входящего сообщения.
     * @param key - ключ входящего сообщения.
     * @return - сохранённое сообщение.
     */
    Inbox writeUniqueMessageAtInbox(KafkaHandlingResultDto resultDto, Long key);

    /**
     * Метод возвращает список непрочитанных входящих сообщений из таблицы Inbox.
     * Будут возвращены только сообщения, поле checkbox которых имеет значение false.
     * @return - список непрочитанных входящих сообщений.
     */
    List<Inbox> getUnreadMessageFromInbox();

    /**
     * Метод помечает сообщение в таблице Inbox как прочитанное после обработки документа.
     * В поле сообщений checkbox устанавливается значение true.
     * @param ids - идентификаторы прочитанных сообщений.
     * @return - список прочитанных сообщений.
     */
    List<Inbox> markMessageAtInboxAsRead(Set<Long> ids);
}
