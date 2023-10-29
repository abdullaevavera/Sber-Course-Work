package ru.abdullaeva.sber.backend.documents.dto;

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
public class Status implements Serializable {
    private String code;
    private String name;

    public static Status of(String code, String name) {
        Status status = new Status();
        status.setCode(code);
        status.setName(name);
        return status;
    }

}
