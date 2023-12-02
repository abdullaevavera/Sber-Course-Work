package ru.abdullaeva.sber.backend.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Идентификатор документа
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdDto implements Serializable {
    private Long id;
}
