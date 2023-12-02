package ru.abdullaeva.sber.backend.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.abdullaeva.sber.backend.kafka.model.Outbox;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройки создания кафка-продюсеров для формирования и отправки сообщений
 */
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfiguration {

    /**
     * Адрес брокера сообщений.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Компонент, отвечающий за создание экземпляров кафка-продюсеров.
     */
    @Bean
    public ProducerFactory<Long, Outbox> producerFactory() {
        Map<String, Object> configurationProperties = new HashMap<>();
        configurationProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configurationProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configurationProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configurationProperties);
    }

    /**
     * Компонент-обёртка, предоставляющий экземплярам продюсеров методы.
     * для отправки сообщений в определённые топики кафка.
     */
    @Bean
    public KafkaTemplate<Long, Outbox> kafkaTemplate(ProducerFactory<Long, Outbox> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
