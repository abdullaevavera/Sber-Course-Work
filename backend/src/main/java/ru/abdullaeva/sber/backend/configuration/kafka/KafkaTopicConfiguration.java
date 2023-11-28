package ru.abdullaeva.sber.backend.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройка создания топиков в кафке
 */
@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {
    /**
     * Адрес брокера сообщений
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Название топика для входящих сообщений
     */
    @Value("${kafka.topics.outbox}")
    private String outboxMessage;

    /**
     * Название топика для исходящих сообщений
     */
    @Value("${kafka.topics.inbox}")
    private String inboxMessage;

    /**
     * Компонент, отвечающий за создание новых топиков в кафка
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    /**
     * Компонент, отвечающий за создание топика, в который
     * будут записываться документы, отправленные в стадию обработки
     */
    @Bean
    public NewTopic outboxMessageTopic() {
        return new NewTopic(outboxMessage, 1, (short) 1);
    }

    /**
     * Компонент, отвечающий за создание топика, из которого
     * будут читаться документы после стадии обработки
     */
    @Bean
    public NewTopic inboxMessageTopic() {
        return new NewTopic(inboxMessage, 1, (short) 1);
    }
}
