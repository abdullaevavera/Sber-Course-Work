package ru.abdullaeva.sber.backend.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;

import java.util.concurrent.ExecutionException;

/**
 * Кафка-продюссер для отправки сообщений в кафка
 */
@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<Long, Outbox> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<Long, Outbox> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Название кафка-топика, в который будет отправлено сообщение.
     */
    @Value("${kafka.topics.inbox}")
    private String topic;

    /**
     * Метод отправляет сообщения в кафка-топик "inbox-mes".
     * При отправке сообщения в кафка используется синхронная стратегия отправки,
     * когда мы ждём от кафка результат отправки во future.get().
     * Сообщением, отправляемым в кафка, является содержимое сообщения из таблицы Outbox.
     * После успешной отправки сообщения в кафка сообщение из таблицы Outbox
     * удаляется, чтобы избежать повторной отправки.
     *
     * @param outboxMessage - содержимое сообщения для отправки. В качестве уникального ключа
     *                      сообщения будет отправлен идентификатор сообщения в Outbox.
     * @return - результат отправки сообщения в кафка.
     */
    public ProducerRecord<Long, Outbox> sendMessage(Outbox outboxMessage) {
        ListenableFuture<SendResult<Long, Outbox>> future
                = kafkaTemplate.send(topic, outboxMessage.getId(), outboxMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<Long, Outbox>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send outbox message to kafka", ex);
            }

            @Override
            public void onSuccess(SendResult<Long, Outbox> result) {
                log.info("Outbox message " + outboxMessage + " sent to kafka in topic " + result.getRecordMetadata().topic());
            }
        });

        try {
            SendResult<Long, Outbox> result = future.get();
            return result.getProducerRecord();
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException("Failed to send message to kafka", ex);
        }
    }
}
