package ru.abdullaeva.sber.backend.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;
import ru.abdullaeva.sber.backend.kafka.model.Inbox;
import ru.abdullaeva.sber.backend.kafka.service.interf.InboxService;

import java.util.Optional;

/**
 * Кафка-консьюмер для чтения сообщений из кафка
 */
@Service
@Slf4j
class KafkaConsumer {
    private final InboxService inboxService;

    @Autowired
    KafkaConsumer(InboxService inboxService) {
        this.inboxService = inboxService;
    }

    /**
     * Метод читает сообщения из кафка-топика "outbox-mes".
     * В топике "outbox-mes" хранятся сообщения о результате обработки отправленного документа.
     * После прочтения сообщения кафка-консьюмером сообщение добавляется в таблицу
     * входящих сообщений Inbox.
     *
     * При добавлении в таблицу Inbox происходит проверка уникальности
     * идентификатора полученного сообщения. Если ообщение с таким идентификатором уже
     * содержится в таблице Inbox, сообщение добавлено не будет. Если сообщение содержит
     * невалидные данные, сообщение так же не будет добавлено в таблицу Inbox и выведется лог.
     *
     * @param resultDto - результат обработки документа в кафка.
     * @param key - уникальный ключ полученного сообщения.
     * @return - записанное в таблицу Inbox входящее сообщение или пустой объект.
     */
    @KafkaListener(topics = "${kafka.topics.outbox}")
    public Optional<Inbox> consumeMessage
            (@Payload KafkaHandlingResultDto resultDto, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Long key) {
        if (StringUtils.isEmpty(resultDto.getCode()) || resultDto.getIdDocument() == null || key == null) {
            log.warn("Inbox message contains invalid data; key: " + key + " inbox message: " + resultDto);
            return Optional.empty();
        }

        Inbox inboxMessage = inboxService.writeUniqueMessageAtInbox(resultDto, key);
        log.info("Inbox message: " + inboxMessage.getMessage() + " with id " + inboxMessage.getId() + " added");
        return Optional.of(inboxMessage);
    }
}
