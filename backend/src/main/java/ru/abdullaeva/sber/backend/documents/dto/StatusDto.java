package ru.abdullaeva.sber.backend.documents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto implements Serializable {
    private String code;
    private String name;

    public static StatusDto of(String code, String name) {
        StatusDto statusDto = new StatusDto();
        statusDto.setCode(code);
        statusDto.setName(name);
        return statusDto;
    }

}
