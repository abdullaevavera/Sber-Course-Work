package ru.abdullaeva.sber.backend.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * Информация о документе после его обработки в кафка
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaHandlingResultDto implements Serializable {

    /**
     * Номер версии для сериализованных данных.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Идентификатор обработанного документа.
     */
    @NotNull
    private Long idDocument;

    /**
     * Статус документа после обработки.
     */
    @NotBlank
    private String code;

    @Override
    public String toString() {
        return "{\"idDocument\" : " + idDocument + ", \"code\" : \"" + code + "\"}";
    }
}
