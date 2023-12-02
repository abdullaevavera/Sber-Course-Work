package ru.abdullaeva.sber.backend.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Статус документа
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable {

    /**
     * Код статуса.
     */
    @NotBlank
    @Length(max = 16)
    private String code;

    /**
     * Название статуса.
     */
    @NotNull
    private String name;

    public static Status of(String code, String name) {
        Status status = new Status();
        status.setCode(code);
        status.setName(name);
        return status;
    }

}
