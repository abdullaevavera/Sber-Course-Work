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


@Service
@Slf4j
class KafkaConsumer {
    /**
     *
     */
    private final InboxService inboxService;

    @Autowired
    KafkaConsumer(InboxService inboxService) {
        this.inboxService = inboxService;
    }

    @KafkaListener(topics = "${kafka.topic-names.outbox}")
    public Inbox consumeMessage
            (@Payload KafkaHandlingResultDto resultDto, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Long key) {
        if (StringUtils.isEmpty(resultDto.getCode()) || resultDto.getIdDocument() == null || key == null) {
            log.warn("Inbox message contains invalid data; key: " + key + " inbox message: " + resultDto);
            return new Inbox();
        }

        Inbox inboxMessage = inboxService.writeUniqueMessageAtInbox(new Inbox(key, resultDto));
        log.info("Inbox message: " + inboxMessage.getMessage() + " with id " + inboxMessage.getId() + " added");
        return inboxMessage;
    }
}
