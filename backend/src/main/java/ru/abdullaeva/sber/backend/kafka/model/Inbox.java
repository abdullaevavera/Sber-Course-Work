package ru.abdullaeva.sber.backend.kafka.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.abdullaeva.sber.backend.kafka.dto.KafkaHandlingResultDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Сущность "таблица входящих сообщений о состоянии документа"
 */
@Entity
@Table(name = "inbox")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Inbox {
    /**
     * Идентификатор входящего сообщения - уникальный ключ полученного из кафка сообщения
     */
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;

    /**
     * Содержание входящего сообщения
     */
    @Column(name = "payload")
    private String message;

    /**
     * Чекбокс для пометки сообщения как прочитанное
     */
    @Column(name = "reading")
    private boolean checkbox;

    public Inbox(Long id, String message) {
        this.id = id;
        this.message = message;
        this.checkbox = false;
    }
}
