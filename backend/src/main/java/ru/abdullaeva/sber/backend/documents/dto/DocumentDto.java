package ru.abdullaeva.sber.backend.documents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Документ
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto implements Serializable {
    /**
     * Идентификатор документа
     */
    private Long id;

    /**
     * Тип документа
     */
    @NotNull
    @Length(max = 256)
    private String type;

    /**
     * Медицинская организация - владелец документа
     */
    @NotNull
    @Length(max = 256)
    private String organization;

    /**
     * Описание документа
     */
    @NotNull
    @Length(max = 512)
    private String description;

    /**
     * Пациент, к которому относится документ
     */
    @NotNull
    @Length(max = 256)
    private String patient;

    /**
     * Дата и время создания документа
     */
    @NotNull
    private Date date;

    /**
     * Статус документа
     */
    @Valid
    private Status status;
}
