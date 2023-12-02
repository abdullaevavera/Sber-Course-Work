package ru.abdullaeva.sber.backend.documents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Сушность "документ"
 */
@Entity
@Table(name = "document")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document {

    /**
     * Идентификатор документа.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Тип документа.
     */
    @Column(name = "type")
    private String type;

    /**
     * Медицинская организация - владелец документа.
     */
    @Column(name = "organization")
    private String organization;

    /**
     * Дата и время создания документа.
     */
    @Column(name = "date")
    private Date date;

    /**
     * Описание документа.
     */
    @Column(name = "description")
    private String description;

    /**
     * Пациент, к которому относится документ.
     */
    @Column(name = "patient")
    private String patient;

    /**
     * Статус документа.
     */
    @Column(name = "status")
    private String status;
}
