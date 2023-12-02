package ru.abdullaeva.sber.backend.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * Список идентификаторов документов
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdsDto implements Serializable {
    private Set<Long> ids;
}
