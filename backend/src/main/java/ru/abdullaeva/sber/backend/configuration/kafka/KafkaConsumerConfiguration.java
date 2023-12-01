package ru.abdullaeva.sber.backend.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройки создания кафка-консьюмеров для чтения и обработки сообщений
 */
@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {
    /**
     * Название группы кафка-консьюмеров
     */
    private static final String CONSUMER_GROUP_ID = "group-id";

    /**
     * Адрес брокера сообщений
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Компонент, отвечающий за создание экземпляров кафка-консьюмеров
     */
    @Bean
    public ConsumerFactory<Long, KafkaHandlingResultDto> consumerFactory() {
        Map<String, Object> configurationProperties = new HashMap<>();
        configurationProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configurationProperties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        configurationProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        configurationProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurationProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>
                (configurationProperties, new LongDeserializer(), new JsonDeserializer<>(KafkaHandlingResultDto.class));
    }

    /**
     * Компонент, отвечающий за потокобезопасность работы кафка-консьюмеров
     * и синхронную фиксацию смещений сообщения в топиках - offset
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, KafkaHandlingResultDto> kafkaListenerContainerFactory(
            ConsumerFactory<Long, KafkaHandlingResultDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, KafkaHandlingResultDto>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setSyncCommits(true);
        return factory;
    }
}
