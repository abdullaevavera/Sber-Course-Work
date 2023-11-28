package ru.abdullaeva.sber.backend.kafka.service.interf;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;

public interface OutboxService {
    /**
     * @param message
     * @return
     */
    Outbox writeMessageAtOutbox(Object message);

    /**
     *
     */
    void sendMessageFromOutbox();
}