package ru.abdullaeva.sber.backend.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;
import ru.abdullaeva.sber.backend.kafka.repository.OutboxRepository;
import ru.abdullaeva.sber.backend.kafka.service.KafkaProducer;
import ru.abdullaeva.sber.backend.kafka.service.interf.OutboxService;

import java.util.List;

@Service
public class OutboxServiceImpl implements OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public OutboxServiceImpl(OutboxRepository outboxRepository, ObjectMapper objectMapper, KafkaProducer kafkaProducer) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Outbox writeMessageAtOutbox(Object message) {
        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to represent the message as a string", e);
        }
        return outboxRepository.save(new Outbox(null, messageJson));
    }

    @Override
    @Scheduled(fixedDelay = 3000)
    public void sendMessageFromOutbox() {
        List<Outbox> outboxMessages = outboxRepository.findAll();
        for (Outbox outboxMessage : outboxMessages) {
            kafkaProducer.sendMessage(outboxMessage);
        }
        outboxRepository.deleteAll(outboxMessages);
    }
}
